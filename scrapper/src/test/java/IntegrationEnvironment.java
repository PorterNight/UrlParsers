import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;

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

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

    static final String MIGRATION_DIR = "migrations";
    static final String MIGRATION_XML = "master.xml";

    static {
        POSTGRESQL_CONTAINER = new PostgreSQLContainer("postgres:14.7")
                .withDatabaseName("db_test")
                .withUsername("username")
                .withPassword("password");
        POSTGRESQL_CONTAINER.start();

        Path migrationsPath = new File(".").toPath().toAbsolutePath().getParent().getParent().resolve(MIGRATION_DIR);
        String changelogPath = MIGRATION_XML;

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