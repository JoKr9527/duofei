package com.duofei.db.mysql.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 拓展 JPA 接口
 * @author duofei
 * @date 2019/9/28
 */
@NoRepositoryBean
public interface ExpandJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    T findOne(String condition, Object... objects);
}
