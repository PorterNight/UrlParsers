import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;

import liquibase.resource.DirectoryResourceAccessor;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

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
    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

    static {
        POSTGRESQL_CONTAINER = new PostgreSQLContainer(DATABASE_IMAGE)
                .withDatabaseName(DATABASE_NAME)
                .withUsername(USERNAME)
                .withPassword(PASSWORD);
        POSTGRESQL_CONTAINER.start();

        // create path to migration XML file
        Path migrationsPath = new File(".").toPath().toAbsolutePath().getParent().getParent().resolve(MIGRATION_DIR);
        String changelogPath = MIGRATION_FILE;

        try (Connection connection = DriverManager.getConnection(
                POSTGRESQL_CONTAINER.getJdbcUrl(),
                POSTGRESQL_CONTAINER.getUsername(),
                POSTGRESQL_CONTAINER.getPassword())) {

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(changelogPath, new DirectoryResourceAccessor(migrationsPath.toFile()), database);
            liquibase.update(new Contexts(), new LabelExpression());

        } catch (Exception e) {
            throw new RuntimeException("Failed to run Liquibase migrations", e);
        } finally {
            POSTGRESQL_CONTAINER.stop();
        }
    }
}