package com.duofei.database.acid;

import com.duofei.database.utils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 原子性模拟测试
 *  更新数据库中的name字段和age字段
 * @author duofei
 * @date 2019/6/29
 */
public class AtomicitySimulation {

    static String UPDATE_NAME = "UPDATE student SET `name` = 'zhangsan'";
    static String UPDATE_AGE = "UPDATE student SET age = 15";
    static String SELECT_ALL = "SELECT * FROM student";

    public static void main(String[] args) throws Exception{
        final Connection connection = DbUtils.getConnection();
        // 无事务情况下：
        /*try {
            noTransactionEnvir(connection);
        } catch (Exception e) {
            System.out.println("异常抛出："+e.getMessage());
        }
        selectAll(connection,"无事务--最终执行结果-@-: ");*/
        // 有事务情况下：
        try {
            transactionEnvir(connection);
        } catch (Exception e) {
            System.out.println("异常抛出："+e.getMessage());
        }
        selectAll(connection,"有事务--最终执行结果-@-: ");

        // 资源清理
        connection.close();
    }

    public static void noTransactionEnvir(Connection connection) throws Exception{
        selectAll(connection,"无事务--初始化-@-: ");
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NAME)){
            int i = preparedStatement.executeUpdate();
            System.out.println("更新name字段成功！影响行数: "+i);
            selectAll(connection,"无事务--更新name-@-: ");
        }catch (Exception e){
            e.printStackTrace();
        }
        throwEx();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AGE)){
            int i = preparedStatement.executeUpdate();
            System.out.println("更新age字段成功！影响行数: "+i);
            selectAll(connection,"无事务--更新name-@-: ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void transactionEnvir(Connection connection) throws Exception{
        selectAll(connection,"有事务--初始化-@-: ");
        connection.setAutoCommit(false);
        Statement statement = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_NAME)){
            int i = preparedStatement.executeUpdate();
            System.out.println("更新name字段！影响行数: "+i);
            selectAll(connection,"有事务--更新name-@-: ");
            throwEx();
            statement = connection.createStatement();
            int i1 = statement.executeUpdate(UPDATE_AGE);
            System.out.println("更新age字段！影响行数: "+i1);
            selectAll(connection,"有事务--更新name-@-: ");
            System.out.println("提交事务");
            // 当执行到该命令后，数据会被真正的提交
            connection.commit();
        }catch (Exception e){
            System.out.println("异常抛出："+e.getMessage());
            // 在数据被真正提交之前，进行回滚操作
            connection.rollback();
        }
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
