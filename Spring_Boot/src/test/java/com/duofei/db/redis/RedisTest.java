package com.duofei.db.redis;

import com.duofei.db.redis.entity.Member;
import com.duofei.db.redis.repositories.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * redis 测试
 * @author duofei
 * @date 2019/9/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisServer redisServer;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void stringOps(){
        redisServer.putValue("author", "duofei");
        String author = redisServer.getValue("author");
        Assert.assertNotNull(author);
    }

    @Test
    public void repositoryOps() {
        Member member = new Member();
        member.setId("001");
        member.setName("duofei");
        member.setAge(24);
        memberRepository.save(member);

        Iterable<Member> result = memberRepository.findAll();
        result.forEach(mb -> {
            System.out.println(mb.getId() + " -.- " + mb.getName());
        } );
        Assert.assertNotNull(result);
    }
}
