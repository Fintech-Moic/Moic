package com.finp.moic.util.dbconfig.rdb;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
                "com.finp.moic.userCard.model.repository",
                "com.finp.moic.userBookMark.model.repository",
        }
)
public class MoicDBConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean
    public DataSource moicDataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty("spring.moicDB.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.moicDB.datasource.jdbc-url"));
        dataSource.setUsername(env.getProperty("spring.moicDB.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.moicDB.datasource.password"));

        return dataSource;
    }

//    @Primary
//    @Bean
//    @ConfigurationProperties("spring.moicDB.datasource")
//    public DataSourceProperties moicDataSourceProperties(){
//        return new DataSourceProperties();
//    }
//
//    @Primary
//    @Bean
//    @ConfigurationProperties("spring.moicDB.datasource.configuration")
//    public DataSource moicDataSourceConfig(){
//        return moicDataSourceProperties()
//                .initializeDataSourceBuilder()
//                .type(HikariDataSource.class)
//                .build();
//    }

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
                "com.finp.moic.userCard.model.entity",
                "com.finp.moic.userBookMark.model.entity",
        });

        bean.setJpaVendorAdapter(vendorAdapter);
        properties.put("hibernate.hbm2ddl.auto",env.getProperty("spring.moicDB.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.moicDB.hibernate.dialect"));
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
