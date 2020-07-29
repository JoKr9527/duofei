package com.duofei;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * rate limiter
 *
 * @author duofei
 * @date 2020/7/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
public class PaceFlowDemo {
    private static final String KEY = "abc";

    private static volatile CountDownLatch countDown;

    private static final Integer requestQps = 1;
    private static final Integer doneTop = 500;
    private static final Integer count = 50;
    private static final AtomicInteger done = new AtomicInteger();
    private static final AtomicInteger pass = new AtomicInteger();
    private static final AtomicInteger block = new AtomicInteger();
    private static volatile boolean stop = false;
    private static volatile boolean stopDefault = false;

    @Test
    public void main() throws Exception {
        System.out.println("pace behavior");
        countDown = new CountDownLatch(1);
        initPaceFlowRule();
        simulatePulseFlow(false);
        countDown.await();

        System.out.println("done");
        System.out.println("total pass:" + pass.get() + ", total block:" + block.get());

        System.out.println();
        System.out.println("default behavior");
        TimeUnit.SECONDS.sleep(5);
        done.set(0);
        pass.set(0);
        block.set(0);
        countDown = new CountDownLatch(1);
        initDefaultFlowRule();
        simulatePulseFlow(true);
        countDown.await();
        System.out.println("done");
        System.out.println("total pass:" + pass.get() + ", total block:" + block.get());
        System.exit(0);
    }

    private static void initPaceFlowRule() {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(KEY);
        rule1.setCount(count);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        /*
         * CONTROL_BEHAVIOR_RATE_LIMITER means requests more than threshold will be queueing in the queue,
         * until the queueing time is more than {@link FlowRule#maxQueueingTimeMs}, the requests will be rejected.
         */
        rule1.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER);
        rule1.setMaxQueueingTimeMs(20*1000);

        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    private static void initDefaultFlowRule() {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(KEY);
        rule1.setCount(count);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        // CONTROL_BEHAVIOR_DEFAULT means requests more than threshold will be rejected immediately.
        rule1.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);

        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    private static void simulatePulseFlow(boolean isDefault) {
        boolean sp = isDefault ? stopDefault : stop;
        for (int i = 0; i < requestQps; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!sp) {
                        long startTime = TimeUtil.currentTimeMillis();
                        Entry entry = null;
                        try {
                            entry = SphU.entry(KEY);
                        } catch (BlockException e1) {
                            block.incrementAndGet();
                        } catch (Exception e2) {
                            // biz exception
                        } finally {
                            if (entry != null) {
                                entry.exit();
                                pass.incrementAndGet();
                                long cost = TimeUtil.currentTimeMillis() - startTime;
                                System.out.println(Thread.currentThread().getName() + "-"+ startTime + "-" +
                                        TimeUtil.currentTimeMillis() + " one request pass, cost " + cost + " ms");
                            }
                        }

                        try {
                            TimeUnit.MILLISECONDS.sleep(5);
                        } catch (InterruptedException e1) {
                            // ignore
                        }

                        if (done.incrementAndGet() >= doneTop) {
                            if (isDefault) {
                                stopDefault = true;
                            } else {
                                stop = true;
                            }
                            countDown.countDown();
                        }
                    }
                }
            }, "Thread " + i);
            thread.start();
        }
    }
}
