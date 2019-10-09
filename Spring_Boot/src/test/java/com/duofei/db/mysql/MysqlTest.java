package com.duofei.db.mysql;

import com.duofei.db.mysql.config.ExpandJpaConfiguration;
import com.duofei.db.mysql.config.JpaConfiguration;
import com.duofei.db.mysql.entity.Deparment;
import com.duofei.db.mysql.entity.Role;
import com.duofei.db.mysql.entity.User;
import com.duofei.db.mysql.repositories.DeparmentRepository;
import com.duofei.db.mysql.repositories.RoleRepository;
import com.duofei.db.mysql.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * mysql 测试
 * @author duofei
 * @date 2019/9/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MysqlTest {

    private static Logger logger = LoggerFactory.getLogger(MysqlTest.class);

    @Autowired
    private DeparmentRepository deparmentRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void initData(){
        userRepository.deleteAll();
        roleRepository.deleteAll();
        deparmentRepository.deleteAll();

        Deparment deparment = new Deparment();
        deparment.setName("开发部");
        deparmentRepository.save(deparment);
        Assert.assertNotNull(deparment.getId());

        Role role = new Role();
        role.setName("admin");
        roleRepository.save(role);
        Assert.assertNotNull(role.getId());

        User user = new User();
        user.setName("user");
        user.setCreateDate(new Date());
        user.setDeparment(deparment);
        List<Role> roles = roleRepository.findAll();
        Assert.assertNotNull(roles);
        user.setRoles(roles);
        userRepository.save(user);
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void pageFind(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<User> users = userRepository.findAll(pageRequest);
        Assert.assertNotNull(users);
        users.getContent().forEach(user ->
            logger.info("===user=== user name:{}, department name:{}, roles : {}",user.getName(),user.getDeparment().getName(),user.getRoles())
        );
    }

    @Test
    public void findAll(){
        List<User> users = jdbcTemplate.query("select * from user", rs ->{
            List<User> result = new ArrayList<>();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setCreateDate(rs.getDate("create_date"));
                user.setName(rs.getString("name"));
                result.add(user);
            }
            return result;
        });
        users.forEach(user ->
            logger.info("===user=== user name:{}, department:{}, roles : {}",user.getName(),user.getDeparment(),user.getRoles())
        );
    }

}
