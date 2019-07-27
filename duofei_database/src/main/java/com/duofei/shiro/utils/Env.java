package com.duofei.shiro.utils;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.ReflectionBuilder;
import org.apache.shiro.env.DefaultEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;

import java.util.HashMap;
import java.util.Map;

/**
 * 构造全局唯一的Environment
 * @author duofei
 * @date 2019/7/10
 */
public class Env {

    private static Environment environment;

    static {
        Ini ini = new Ini();
        ini.loadFromPath("classpath:shiro.ini");

        ReflectionBuilder builder = new ReflectionBuilder();
        Map<String, Object> objects = new HashMap<>();
        objects.put("iniRealm",new IniRealm(ini));
        builder.setObjects(objects);
        builder.buildObjects(ini.getSection("main"));

        environment = new DefaultEnvironment(builder.getObjects());
    }

    public SecurityManager getSecurityManager() throws IllegalStateException {
        return environment.getSecurityManager();
    }
}
