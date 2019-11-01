package com.duofei.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

/**
 * 基于继承的命令实现
 * @author duofei
 * @date 2019/10/26
 */
public class HelloCommand extends HystrixCommand<String> {

    private static final HystrixCommandKey commandKey =  HystrixCommandKey.Factory.asKey("helloCommandKey");

    private RestTemplate restTemplate;

    public HelloCommand(Setter setter, RestTemplate restTemplate){
        super(setter.andCommandKey(commandKey));
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        System.out.println("我执行了额");
        return restTemplate.getForEntity("http://HELLO-SERVICE/provider/hello1?name=luoluo", String.class).getBody();
    }

    @Override
    protected String getFallback() {
        Throwable executionException = getExecutionException();
        executionException.printStackTrace();
        return "我是备用值哦";
    }

    @Override
    public Throwable getExecutionException() {
        return super.getExecutionException();
    }

    @Override
    protected String getCacheKey() {
        // 在同一http请求中，每次都返回缓存值，实际使用中，应根据方法参数来构造
        return "all";
    }

    @Override
    public HystrixCommandKey getCommandKey() {
        return commandKey;
    }

    /**
     * 清理缓存
     * 开启请求缓存之后，我们在读的过程中没有问题，但是我们如果是写，那么我们继续读之前的缓存了
     * 我们需要把之前的cache清掉
     * 说明 ：   1.其中getInstance方法中的第一个参数的key名称要与实际相同
     *          2.clear方法中的cacheKey要与getCacheKey方法生成的key方法相同
     *          3.注意我们用了commandKey是helloCommandKey,大家要注意之后new这个Command的时候要指定相同的commandKey,否则会清除不成功
     */
    public static void flushRequestCache(){
        HystrixRequestCache.getInstance(commandKey
                , HystrixConcurrencyStrategyDefault.getInstance())
                .clear("all");
    }
}
