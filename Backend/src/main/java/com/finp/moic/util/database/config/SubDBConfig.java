package com.finp.moic.util.database.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "moicSecurityEntityManager",
        transactionManagerRef = "moicSecurityTransactionManager",

        /* 혜지 : 패키지 추가 완료 */
        basePackages = {"com.finp.moic.util.security.repository",}
)
public class SubDBConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource moicSecurityDataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.sub.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.sub.datasource.jdbc-url"));
        dataSource.setUsername(env.getProperty("spring.sub.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.sub.datasource.password"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean moicSecurityEntityManager(){
        LocalContainerEntityManagerFactoryBean bean
                =new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter vendorAdapter
                =new HibernateJpaVendorAdapter();
        HashMap<String, Object> properties=new HashMap<>();

        bean.setDataSource(moicSecurityDataSource());

        /* 혜지 : 패키지 추가 완료 */
        bean.setPackagesToScan(new String[]{"com.finp.moic.util.security.entity"});

        bean.setJpaVendorAdapter(vendorAdapter);
        properties.put("hibernate.hbm2ddl.auto",env.getProperty("spring.sub.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.sub.hibernate.dialect"));
        bean.setJpaPropertyMap(properties);
        return bean;
    }

    @Bean
    public PlatformTransactionManager moicSecurityTransactionManager(){
        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(moicSecurityEntityManager().getObject());
        return transactionManager;
    }
}
