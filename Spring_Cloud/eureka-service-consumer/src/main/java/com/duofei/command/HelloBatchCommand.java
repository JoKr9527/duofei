package com.duofei.command;

import com.duofei.service.ConsumerService;
import com.netflix.hystrix.HystrixCommand;

import java.util.List;

/**
 * 批量请求命令
 * @author duofei
 * @date 2019/10/28
 */
public class HelloBatchCommand extends HystrixCommand<List<String>> {

    private ConsumerService service;

    private List<String> names;

    public HelloBatchCommand(ConsumerService service, List<String> names){
        super(Setter.withGroupKey(() -> "helloServiceCommand"));
        this.service = service;
        this.names = names;
    }

    @Override
    protected List<String> run() throws Exception {
        return service.getNames(names);
    }
}
