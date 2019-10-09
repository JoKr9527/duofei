package com.duofei.db.redis.repositories;

import com.duofei.db.redis.entity.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author duofei
 * @date 2019/9/27
 */
@Repository
public interface MemberRepository extends CrudRepository<Member,String> {
}
