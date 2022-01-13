package sh.love.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JpaDatabaseConfig {

    @Bean(name = "datasourceJpa")
    @ConfigurationProperties(prefix = "spring.datasource.jpa-config")
    public DataSource dataSourceJpa() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerFactoryJpa")
    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSourceJpa())
                .packages("sh.love.jpa.domain")
                .build();
    }

    @Bean(name = "transactionManagerJpa")
    PlatformTransactionManager TransactionManagerMain(
            EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(jpaEntityManagerFactory(builder).getObject());
    }

    @Configuration
    @EnableJpaRepositories(
            basePackages="sh.love.jpa.repository",
            entityManagerFactoryRef = "entityManagerFactoryJpa",
            transactionManagerRef = "transactionManagerJpa")
    static class JpaRepositoriesConfig {
    }
}
