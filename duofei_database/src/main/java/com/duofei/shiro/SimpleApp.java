package com.duofei.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.ReflectionBuilder;
import org.apache.shiro.env.DefaultEnvironment;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * shiro 1.4.1 应用程序
 * 解决了官网demo使用过时类的问题
 * @author duofei
 * @date 2019/7/9
 */
public class SimpleApp {

    static Logger log = LoggerFactory.getLogger(SimpleApp.class);

    public static void main(String[] args) {
        // IniSecurityManagerFactory 已经标记为过时, 推荐使用Environment
        Ini ini = new Ini();
        // 从类路径下加载 ini 文件
        ini.loadFromPath("classpath:shiro.ini");

        // 创建 environment 对象
        ReflectionBuilder builder = new ReflectionBuilder();
        Map<String, Object> objects = new HashMap<>();
        objects.put("iniRealm",new IniRealm(ini));
        builder.setObjects(objects);
        builder.buildObjects(ini.getSection("main"));

        Environment environment = new DefaultEnvironment(builder.getObjects());
        SecurityManager securityManager = environment.getSecurityManager();
        SecurityUtils.setSecurityManager(securityManager);
        if(securityManager instanceof RealmSecurityManager){
            securityManager = (RealmSecurityManager)securityManager;
            ((RealmSecurityManager) securityManager).setRealm(((DefaultEnvironment) environment).getObject("iniRealm",IniRealm.class));
        }

        // 获取当前正在执行的 user
        Subject currentUser = SecurityUtils.getSubject();

        // 使用 session 做一些事情，并不需要web或者EJB 容器
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }

        // 登录当前用户 检查角色和权限
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        //test a role:
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        //test a typed permission (not instance-level)
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //all done - log out!
        currentUser.logout();

        System.exit(0);
    }
}
