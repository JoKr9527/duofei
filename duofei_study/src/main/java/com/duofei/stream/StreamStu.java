package com.duofei.stream;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream 类学习测试
 * @author duofei
 * @date 2019/7/27
 */
public class StreamStu {

    /**
     * 验证 Collect方法的三个参数作用
     *
     * @author duofei
     * @date 2019/7/27
     */
    @Test
    public void testCollect(){
        Stream<String> stream = Stream.of("I", "Love", "You", "!");
        // 如果不做强制转换，生成的list会是 List<Object>
        List<Integer> result = stream.collect((Supplier<List<Integer>>) ArrayList::new, (list, s) ->
                list.add(s.length()), (list1, list2) -> list1.addAll(list2));
        result.forEach(System.out::println);

    }

    @Test
    public void testCollect1(){
        // 测试 第三个参数 combiner 是否只在并行流情况下执行
        IntStream range = IntStream.range(0, 10);
        range.collect(ArrayList::new, List::add, (list1, list2) -> {
            System.out.println(Thread.currentThread().getName()+"执行了");
            list1.addAll(list2);
        });
        System.out.println("并行流：");
        AtomicInteger count = new AtomicInteger(1);
        IntStream.range(0, 10).parallel().collect(ArrayList::new, List::add, (list1, list2) -> {
            System.out.println(Thread.currentThread().getName()+"执行了"+count.getAndDecrement());
            list1.addAll(list2);
        });
        System.out.println("count: "+ count.get());
    }

    @Test
    public void testCollectDiff(){
        // 测试 两种collect 方法的不同
        Stream<String> stream1 = Stream.of("I", "Love", "You", "!");
        List<String> result = stream1.collect((Supplier<List<String>>)ArrayList::new, List::add, (list1, list2) -> list1.addAll(list2));
        result.forEach(System.out::println);
        System.out.println("使用Collectors：");
        Stream<String> stream2 = Stream.of("I", "Love", "You", "!");
        List<Integer> result2 = stream2.collect(new Collector<String, List<String>, List<Integer>>() {
            @Override
            public Supplier<List<String>> supplier() {
                return ()->{
                    System.out.println("产生容器：list");
                    return new ArrayList<>();
                };
            }

            @Override
            public BiConsumer<List<String>, String> accumulator() {
                return (list,s)->{
                    System.out.println("往容器中添加元素");
                    list.add(s);
                };
            }

            @Override
            public BinaryOperator<List<String>> combiner() {
                return (list1, list2) -> {
                    System.out.println("合并结果集");
                    list1.addAll(list2);
                    return list1;
                };
            }

            @Override
            public Function<List<String>, List<Integer>> finisher() {
                return r1->{
                    System.out.println("我将追加后缀：hha");
                    List<Integer> r2 = new ArrayList<>();
                    r2.add(r1.size());
                    return r2;
                };
            }

            /**
             * 如果在这里指定了 characteristics 集包含了 IDENTITY_FINISH，那么将不会执行 finisher方法
             * 由于泛型的原因，仍然能够返回 List<Integer> ,最终打印的时候会报类型转换错误
             * @author duofei
             * @date 2019/7/27
             * @return void
             */
            @Override
            public Set<Characteristics> characteristics() {
                //return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
                return new HashSet<>();
            }
        });
        result2.forEach(System.out::println);
    }

    @Test
    public <T> void toSet(){
        BinaryOperator<Set<T>> combiner = (Set<T> left, Set<T> right) -> {
            left.addAll(right);
            return left;
        };
        Collector<T, ?, Set<T>> toSet = Collector.of(HashSet::new, Set::add,combiner,
                Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH);
    }

}
