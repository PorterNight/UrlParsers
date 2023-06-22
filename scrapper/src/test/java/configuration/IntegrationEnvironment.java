package configuration;

import jakarta.persistence.EntityManagerFactory;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@Testcontainers
@ContextConfiguration(classes = IntegrationEnvironment.IntegrationEnvironmentConfig.class)
public abstract class IntegrationEnvironment {

    static final Properties PROPERTIES = new Properties();

    // load configuration
    static {

        try (InputStream input = IntegrationEnvironment.class.getClassLoader().getResourceAsStream("config.properties")) {
            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    static final String MIGRATION_DIR = PROPERTIES.getProperty("migrationDir");
    static final String MIGRATION_FILE = PROPERTIES.getProperty("migrationFile");
    static final String DATABASE_IMAGE = PROPERTIES.getProperty("testsContainer.databaseImage");
    static final String DATABASE_NAME = PROPERTIES.getProperty("testsContainer.databaseName");
    static final String USERNAME = PROPERTIES.getProperty("testsContainer.username");
    static final String PASSWORD = PROPERTIES.getProperty("testsContainer.password");

    @Container
    public static final SingletonPostgreSQLContainer POSTGRESQL_CONTAINER = SingletonPostgreSQLContainer.getInstance();

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @BeforeAll
    static void init() {

        // create path to migration XML file
        Path migrationsPath = new File(".").toPath().toAbsolutePath().getParent().getParent().resolve(MIGRATION_DIR);
        String changelogPath = MIGRATION_FILE;

        System.out.println("configuration.IntegrationEnvironment: path" + migrationsPath.toString());

        try (Connection connection = DriverManager.getConnection(
                POSTGRESQL_CONTAINER.getJdbcUrl(),
                POSTGRESQL_CONTAINER.getUsername(),
                POSTGRESQL_CONTAINER.getPassword())) {

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(changelogPath, new DirectoryResourceAccessor(migrationsPath.toFile()), database);

            liquibase.update(new Contexts(), new LabelExpression());

        } catch (Exception e) {
            throw new RuntimeException("Failed to run Liquibase migrations", e);
        }
    }


    @Configuration
    public static class IntegrationEnvironmentConfig {

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.testcontainers.jdbc.ContainerDatabaseDriver");
            dataSource.setUrl(POSTGRESQL_CONTAINER.getJdbcUrl());
            dataSource.setUsername(POSTGRESQL_CONTAINER.getUsername());
            dataSource.setPassword(POSTGRESQL_CONTAINER.getPassword());
            return dataSource;
        }

        @Bean
        public PlatformTransactionManager jdbcTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
            JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory);
            return transactionManager;
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource());
            em.setPackagesToScan(new String[]{"ru.tinkoff.edu.java.scrapper.domain.jpa"});
            JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            em.setJpaVendorAdapter(vendorAdapter);
            em.setJpaProperties(additionalJpaProperties());
            return em;
        }

        Properties additionalJpaProperties() {
            Properties properties = new Properties();
            properties.setProperty("hibernate.hbm2ddl.auto", "none");
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.format_sql", "true");
            return properties;
        }
    }
}