package com.duofei.database.acid;

import com.duofei.database.utils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

/**
 * 事务隔离级别模拟
 * @author duofei
 * @date 2019/7/3
 */
public class TcIsoLevelSimulation {

    static Integer id = 202;
    static String SELECT_ALL = "SELECT * FROM student";
    static String UPDATE_AGE = "UPDATE student SET age = age+1";

    private static final ExecutorService poolExecutor = new ThreadPoolExecutor(2, 5, 1,
            TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>(), r->new Thread(r,"tcIsoLevel"));

    public static void main(String[] args) {
        Connection a = DbUtils.getConnection();
        Connection b = DbUtils.getConnection();

        //修改数据库的隔离级别 运行：help ISOLATION 按指示操作
        // a 读取到了事务b未提交的数据
        //dirtyRead(a,b);
        // a 在同一事务中读取的数据不一致
        //unrepeatableRead(a,b);
        phantomRead(a,b);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        poolExecutor.shutdown();
    }

    /**
     * 脏读模拟
     * @author duofei
     * @date 2019/7/3 16:10
     * @param
     * @return
     * @throws
     */
    private static void dirtyRead(Connection a,Connection b){
        // A事务读取到B事务更新的数据，然后B回滚操作，那么A读取到的数据是脏数据。
        CountDownLatch aContinue = new CountDownLatch(1);
        CountDownLatch bContinue = new CountDownLatch(1);
        CountDownLatch aContinue1 = new CountDownLatch(1);
        poolExecutor.execute(()->{
            try {
                a.setAutoCommit(false);
                aContinue.await();
                selectAll(a,"a在b修改数据后回滚之前查询所有数据：");
                bContinue.countDown();
                a.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    a.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                aContinue1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            selectAll(a,"a在b回滚之后查询所有数据：");
        });
        poolExecutor.execute(()->{
            PreparedStatement preparedStatement = null;
            try {
                b.setAutoCommit(false);
                preparedStatement = b.prepareStatement(UPDATE_AGE);
                preparedStatement.executeUpdate();
                aContinue.countDown();
                bContinue.await();
                // 模拟异常抛出，导致回滚
                throwEx();
                b.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    b.rollback();
                    aContinue1.countDown();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 不可重复读模拟
     * @author duofei
     * @date 2019/7/3 16:11
     * @param
     * @return
     * @throws
     */
    private static void unrepeatableRead(Connection a,Connection b){
        // A事务多次读取同一数据，事务B在事务A读取的过程中。对事务作了更新并提交，导致事务A 多次读取同一数据时，结果不一致
        CountDownLatch aContinue = new CountDownLatch(1);
        CountDownLatch bContinue = new CountDownLatch(1);
        CountDownLatch aContinue1 = new CountDownLatch(1);
        CountDownLatch aContinue2 = new CountDownLatch(1);
        poolExecutor.execute(()->{
            try {
                a.setAutoCommit(false);
                aContinue.await();
                selectAll(a,"a在b修改数据前查询所有数据：");
                aContinue1.await();
                selectAll(a,"a在b修改数据之后，提交事务之前查询所有数据：");
                bContinue.countDown();
                aContinue2.await();
                selectAll(a,"a在b提交事务之后查询所有数据：");
                a.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    a.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });
        poolExecutor.execute(()->{
            PreparedStatement preparedStatement = null;
            try {
                b.setAutoCommit(false);
                aContinue.countDown();
                preparedStatement = b.prepareStatement(UPDATE_AGE);
                preparedStatement.executeUpdate();
                aContinue1.countDown();
                preparedStatement = b.prepareStatement(UPDATE_AGE);
                preparedStatement.executeUpdate();
                bContinue.await();
                b.commit();
                aContinue2.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    b.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 幻读模拟
     * @author duofei
     * @date 2019/7/3 16:12
     * @param
     * @return
     * @throws
     */
    private static void phantomRead(Connection a,Connection b){
        // 当事务A读取数据，事务B在此时插入了一条数据，事务a再次读取数据，发现还有一条之前没有读到过的数据，像产生幻觉一样
        CountDownLatch aContinue = new CountDownLatch(1);
        CountDownLatch aContinue1 = new CountDownLatch(1);
        CountDownLatch bContinue = new CountDownLatch(1);
        // 数据准备
        try{
            PreparedStatement prstat = a.prepareStatement(insertOne());
            prstat.executeUpdate();
            PreparedStatement preparedStatement = a.prepareStatement(insertOne());
            preparedStatement.executeUpdate();
            prstat.close();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        selectAll(a,"初始化数据查询：");

        poolExecutor.execute(()->{
            try {
                a.setAutoCommit(false);
                aContinue.await();
                selectAll(a,"a在b修改数据前查询所有数据：");
                bContinue.countDown();
                aContinue1.await();
                selectAll(a,"a在事务b提交完毕查询数据：");
                a.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    a.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        poolExecutor.execute(()->{
            PreparedStatement preparedStatement = null;
            try {
                b.setAutoCommit(false);
                aContinue.countDown();
                bContinue.await();
                preparedStatement = b.prepareStatement(insertOne());
                preparedStatement.executeUpdate();
                b.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    b.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            aContinue1.countDown();
            selectAll(b,"事务b提交完事务查询数据：");
        });
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

    private static String insertOne(){
        return "INSERT INTO student VALUES('"+(++id)+"','wang"+id+"',"+id+",'class"+id+"')";
    }
}
