package com.duofei.db.mongodb;

import com.duofei.db.mongodb.entity.Admin;
import com.duofei.db.mongodb.repository.AdminRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author duofei
 * @date 2019/9/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbTest {

    private static Logger logger = LoggerFactory.getLogger(MongodbTest.class);
    @Autowired
    private AdminRepository adminRepository;

    @Before
    public void setup(){
        HashSet<String> roles = new HashSet<>();
        roles.add("manage");
        Admin admin = new Admin("1", "duofei", "545454", "324@qq.com", new Date(), roles);
        adminRepository.save(admin);
    }

    @Test
    public void findAll(){
        List<Admin> admins = adminRepository.findAll();
        Assert.assertNotNull(admins);
        admins.forEach(admin -> {
            logger.info("===admin=== adminId:{},userName:{},pass:{},email:{},registDate:{},roles:{}",
                    admin.getAdminId(), admin.getUsername(), admin.getPassword(), admin.getEmail(), admin.getRegistrationDate(), admin.getRoles());
        });
    }
}
