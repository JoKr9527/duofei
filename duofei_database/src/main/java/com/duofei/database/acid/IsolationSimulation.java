package com.duofei.database.acid;

import com.duofei.database.utils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

/**
 * 隔离性模拟
 * 线程一更新age 字段，使用线程协调工具，保证线程二在线程一更新完age 字段后，再执行查询和更新age为age+1字段操作。
 * @author duofei
 * @date 2019/6/29
 */
public class IsolationSimulation {

    static String SELECT_ALL = "SELECT * FROM student";
    static String UPDATE_AGE = "UPDATE student SET age = age+1";

    private static final ExecutorService poolExecutor = new ThreadPoolExecutor(2, 5, 1,
            TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>(), r->new Thread(r,"隔离性测试线程"));


    public static void main(String[] args) {
        CountDownLatch thread1Continue = new CountDownLatch(1);
        CountDownLatch thread2Continue = new CountDownLatch(1);
        CountDownLatch mainConinue = new CountDownLatch(2);

        final Connection connection00 = DbUtils.getConnection();
        selectAll(connection00,"初始化--");

        final Connection connection1 = DbUtils.getConnection();
        poolExecutor.execute(()->{
            try {
                connection1.setAutoCommit(false);
                PreparedStatement preparedStatement = connection1.prepareStatement(UPDATE_AGE);
                preparedStatement.executeUpdate();
                thread2Continue.countDown();
                thread1Continue.await();
                System.out.println("线程1开始事务提交");
                connection1.commit();
                preparedStatement.close();
                System.out.println("线程1执行完毕");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    connection1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                mainConinue.countDown();
            }
        });

        final Connection connection2 = DbUtils.getConnection();
        poolExecutor.execute(()->{
            try {
                connection2.setAutoCommit(false);
                thread2Continue.await();
                selectAll(connection2, "线程2");
                // b2 当放在这里时，会在线程一提交事务之前读取数据，并不会读到线程一事务中修改的数据，但线程2最终更新值会成功(在线程一更新后的基础上加一)
                thread1Continue.countDown();
                // a 在这里已经开始请求事务锁了
                PreparedStatement preparedStatement = connection2.prepareStatement(UPDATE_AGE);
                preparedStatement.executeUpdate();
                // b1 放在这里会导致线程一会阻塞，无法释放锁，由于在a处已经开始请求事务锁了，所以最终会在a处抛出异常
                //thread1Continue.countDown();
                System.out.println("线程2开始事务提交");
                connection2.commit();
                preparedStatement.close();
                System.out.println("线程2执行完毕");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    connection2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                mainConinue.countDown();
            }
        });

        try {
            mainConinue.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        selectAll(connection00,"结果--");

    }

    private static void selectAll(Connection connection , String prefix){
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(prefix+"name:"+resultSet.getString(2)+",age:"+resultSet.getInt(3));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void throwEx() throws Exception{
        // 模拟异常发生
        throw new Exception("我收到了程序员大大给的异常指令");
    }
}
