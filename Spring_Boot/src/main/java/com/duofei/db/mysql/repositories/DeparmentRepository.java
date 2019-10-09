package com.duofei.db.mysql.repositories;

import com.duofei.db.mysql.entity.Deparment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * deparment jpa 库
 * @author duofei
 * @date 2019/9/27
 */
@Repository
public interface DeparmentRepository extends JpaRepository<Deparment,Long> {
}
