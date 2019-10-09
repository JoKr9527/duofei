package com.duofei.db.mongodb.repository;

import com.duofei.db.mongodb.entity.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * admin
 * @author duofei
 * @date 2019/9/28
 */
@Repository
public interface AdminRepository extends MongoRepository<Admin,String> {
}
