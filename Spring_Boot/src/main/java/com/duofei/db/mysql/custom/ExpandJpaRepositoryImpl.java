package com.duofei.db.mysql.custom;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 拓展 JPA 接口的实现类
 * @author duofei
 * @date 2019/9/28
 */
public class ExpandJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T,ID>
    implements ExpandJpaRepository<T, ID> {

    private final EntityManager entityManager;
    private final JpaEntityInformation<T,?> entityInformation;

    public ExpandJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.entityInformation = entityInformation;
        this.entityManager = em;
    }

    @Override
    public T findOne(String condition, Object... objects) {
        System.out.println("find one 执行---" + condition);
        return null;
    }
}
