package com.finp.moic.util.database.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "moicEntityManager",
        transactionManagerRef = "moicTransactionManager",

        /* 혜지 : 작성 시마다 패키지 추가 필요 */
        basePackages = {
                "com.finp.moic.user.model.repository",
                "com.finp.moic.card.model.repository",
                "com.finp.moic.cardBenefit.model.repository",
                "com.finp.moic.giftCard.model.repository",
                "com.finp.moic.shop.model.repository",
                "com.finp.moic.userBookMark.model.repository",
        }
)
public class MainDBConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean
    public DataSource moicDataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.main.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.main.datasource.jdbc-url"));
        dataSource.setUsername(env.getProperty("spring.main.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.main.datasource.password"));

        return dataSource;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean moicEntityManager(){
        LocalContainerEntityManagerFactoryBean bean
                =new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter vendorAdapter
                =new HibernateJpaVendorAdapter();
        HashMap<String, Object> properties=new HashMap<>();

        bean.setDataSource(moicDataSource());

        /* 혜지 : 작성 시마다 패키지 추가 필요 */
        bean.setPackagesToScan(new String[]{
                "com.finp.moic.user.model.entity",
                "com.finp.moic.card.model.entity",
                "com.finp.moic.cardBenefit.model.entity",
                "com.finp.moic.giftCard.model.entity",
                "com.finp.moic.shop.model.entity",
                "com.finp.moic.userBookMark.model.entity",
        });

        bean.setJpaVendorAdapter(vendorAdapter);
        properties.put("hibernate.hbm2ddl.auto",env.getProperty("spring.main.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.main.hibernate.dialect"));
        bean.setJpaPropertyMap(properties);
        return bean;
    }

    @Primary
    @Bean
    public PlatformTransactionManager moicTransactionManager(){
        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(moicEntityManager().getObject());
        return transactionManager;
    }
}
