package com.tenutz.storemngsim.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "smsdbEntityManager",
        transactionManagerRef = "smsdbTransactionManager",
        basePackages = {
                "com.tenutz.storemngsim.domain.base",
                "com.tenutz.storemngsim.domain.common.enums",
                "com.tenutz.storemngsim.domain.menu",
                "com.tenutz.storemngsim.domain.refreshtoken",
                "com.tenutz.storemngsim.domain.sales",
                "com.tenutz.storemngsim.domain.store",
                "com.tenutz.storemngsim.domain.user"
        }
)
@RequiredArgsConstructor
public class MasterDatabaseConfig {

    private final Environment env;

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.hikari.smsdb")
    public DataSource smsdbDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager smsdbTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(smsdbEntityManager().getObject());

        return transactionManager;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean smsdbEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(smsdbDataSource());
        em.setPackagesToScan(
                "com.tenutz.storemngsim.domain.base",
                "com.tenutz.storemngsim.domain.common.enums",
                "com.tenutz.storemngsim.domain.menu",
                "com.tenutz.storemngsim.domain.refreshtoken",
                "com.tenutz.storemngsim.domain.sales",
                "com.tenutz.storemngsim.domain.store",
                "com.tenutz.storemngsim.domain.user"
        );

        em.setPersistenceUnitName("smsdbEntityManager");

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(adapter);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.smsdb.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }
}
