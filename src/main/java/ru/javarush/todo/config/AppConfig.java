package ru.javarush.todo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class AppConfig {

    @Value("${sessionFactory.setPackagesToScan}")
    String packagesToScan;

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(packagesToScan);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Value("${hibernate.properties.dialect}")
    String dialect;
    @Value("${hibernate.properties.driver}")
    String driver;
    @Value("${hibernate.properties.hbm2ddl_auto}")
    String hbm2ddl;
    @Value("${spring.jpa.show-sql}")
    String showSql;

    @Bean
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, dialect);
        properties.put(AvailableSettings.DRIVER, driver);
        properties.put(AvailableSettings.HBM2DDL_AUTO, hbm2ddl);
        properties.put(AvailableSettings.SHOW_SQL, showSql);
        return properties;
    }

    @Value("${spring.datasource.url}")
    String jdbcUrl;
    @Value("${spring.datasource.user}")
    String dbUser;
    @Value("${spring.datasource.password}")
    String dbPass;
    @Value("${spring.datasource.driver-class-name}")
    String dbDriverClassName;

    @Bean
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPass);
        config.setDriverClassName(dbDriverClassName);
        return new HikariDataSource(config);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }
}
