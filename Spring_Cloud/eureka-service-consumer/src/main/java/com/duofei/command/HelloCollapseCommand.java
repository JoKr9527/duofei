package com.duofei.command;

import com.duofei.service.ConsumerService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请求合并命令
 * @author duofei
 * @date 2019/10/28
 */
public class HelloCollapseCommand extends HystrixCollapser<List<String>, String, String> {

    private ConsumerService consumerService;
    private String name;

    public HelloCollapseCommand(ConsumerService consumerService, String name) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userCollapseCommand"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(2000))
                .andScope(Scope.GLOBAL)
                );
        this.consumerService = consumerService;
        this.name = name;
    }

    @Override
    public String getRequestArgument() {
        return name;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, String>> collapsedRequests) {
        /**
         * collapsedRequests 保存了延迟时间窗中收集到的所有获取单个 name 的请求,
         * 通过获取这些参数，来组织批量请求命令 HelloBatchCommand 实例
         */
        return new HelloBatchCommand(consumerService, collapsedRequests.stream()
                .map(CollapsedRequest::getArgument).collect(Collectors.toList()));
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, String>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<String, String> collapsedRequest : collapsedRequests) {
            collapsedRequest.setResponse(batchResponse.get(count++));
        }
    }
}
