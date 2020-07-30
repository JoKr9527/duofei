package com.duofei;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.slots.block.flow.ClusterFlowConfig;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import org.apache.catalina.Cluster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.List;

/**
 * @author duofei
 * @date 2020/7/29
 */
@SpringBootApplication
public class TokenServerApplication {

    private static final String SPECIAL_NAMESPACE = "sentinel-app";

    public static void main(String[] args) throws Exception {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(TokenServerApplication.class, args);
        System.out.println(applicationContext.getEnvironment().getProperty("spring.application.name"));
        ClusterTokenServer tokenServer = new SentinelDefaultTokenServer();
        //传输配置
        ClusterServerConfigManager.loadGlobalTransportConfig(new ServerTransportConfig()
                .setIdleSeconds(600).setPort(18730));
        ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton(SPECIAL_NAMESPACE));

        ClusterFlowRuleManager.loadRules(SPECIAL_NAMESPACE, buildFlowRuleLikeClient());
        tokenServer.start();
    }

    private static List<FlowRule> buildFlowRuleLikeClient(){
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("hello");
        flowRule.setGrade(1);
        flowRule.setCount(3.0);
        flowRule.setControlBehavior(0);
        flowRule.setWarmUpPeriodSec(10);
        flowRule.setMaxQueueingTimeMs(500);
        flowRule.setClusterMode(true);
        flowRule.setLimitApp("default");
        //这里的 187 是我在集群限流客户端端点发现的，具体 FlowRuleChecker#passClusterCheck()
        flowRule.setClusterConfig(new ClusterFlowConfig().setFlowId(194L).setThresholdType(1)
                .setFallbackToLocalWhenFail(true).setStrategy(0)
                .setSampleCount(10).setWindowIntervalMs(1000));
        return Collections.singletonList(flowRule);
    }
}
