package com.duofei.derby;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * derby 数据库基本配置
 * @author duofei
 * @date 2019/11/25
 */
@ConfigurationProperties(prefix = "spring.derby")
public class DerbyProperties {

    private String drivers = "org.apache.derby.jdbc.ClientDriver";

    private String url = "jdbc:derby://localhost:1527/COREJAVA;create=true";

    private String userName = "";

    private String password = "";

    public String getDrivers() {
        return drivers;
    }

    public void setDrivers(String drivers) {
        this.drivers = drivers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
