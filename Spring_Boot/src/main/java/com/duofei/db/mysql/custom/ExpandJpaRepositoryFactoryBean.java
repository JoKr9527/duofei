package com.duofei.db.mysql.custom;

import com.duofei.db.mysql.custom.ExpandJpaRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * @author duofei
 * @date 2019/9/28
 */
public class ExpandJpaRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable> extends JpaRepositoryFactoryBean {

    public ExpandJpaRepositoryFactoryBean(Class repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new ExpandJpaRepositoryFactory<T, ID>(entityManager);
    }

    private static class ExpandJpaRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory{
        private final EntityManager entityManager;

        public ExpandJpaRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information,
                                                                        EntityManager entityManager) {

            JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
            return new ExpandJpaRepositoryImpl(entityInformation,entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return ExpandJpaRepositoryImpl.class;
        }
    }
}
