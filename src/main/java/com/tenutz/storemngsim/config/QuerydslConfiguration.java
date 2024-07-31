package com.tenutz.storemngsim.config;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QuerydslConfiguration {

    @PersistenceContext(unitName = "smsdbEntityManager")
    private EntityManager smsdbEm;

    @PersistenceContext(unitName = "mmsdbEntityManager")
    private EntityManager mmsdbEm;

    @Bean
    public JPAQueryFactory smsdbJpaQueryFactory() {
        return new JPAQueryFactory(JPQLTemplates.DEFAULT, smsdbEm);
    }

    @Bean
    public JPAQueryFactory mmsdbJpaQueryFactory() {
        return new JPAQueryFactory(JPQLTemplates.DEFAULT, mmsdbEm);
    }
}
