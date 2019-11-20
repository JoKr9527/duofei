package com.duofei.endpoint;

import com.duofei.app.Author;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;

/**
 * 暴露app介绍信息端点
 * @author duofei
 * @date 2019/11/19
 */
@Endpoint(id = "appInfo")
public class AppInfoEndpoint {

    /**
     * app 描述信息
     */
    private String desc;
    /**
     * app 作者信息
     */
    private Author author;

    public AppInfoEndpoint(String desc, Author author){
        this.desc = desc;
        this.author = author;
    }

    /**
     * 读取描述： HTTP GET请求：http://localhost:8089/actuator/appInfo
     */
    @ReadOperation
    public String getDesc(){
        return this.desc;
    }

    /**
     * 读取作者信息：HTTP GET请求：http://localhost:8089/actuator/appInfo/1
     */
    @ReadOperation
    public Author getAuthor(@Selector Integer type ){
        if(type.intValue() == 1){
            return author;
        }
        return null;
    }

    /**
     * 修改作者姓名，并返回旧的名称：HTTP POST请求：http://localhost:8089/actuator/appInfo/DUO
     */
    @WriteOperation
    public String updateAuthor(@Selector String name){
        String oldName = author.getName();
        this.author.setName(name);
        return oldName;
    }

}
