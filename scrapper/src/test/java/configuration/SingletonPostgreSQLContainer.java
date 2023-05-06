package configuration;

import org.testcontainers.containers.PostgreSQLContainer;

public class SingletonPostgreSQLContainer extends PostgreSQLContainer<SingletonPostgreSQLContainer> {
    private static final String IMAGE_VERSION = "postgres:14.7";
    private static SingletonPostgreSQLContainer container;

    private SingletonPostgreSQLContainer() {
        super(IMAGE_VERSION);
    }

    public static SingletonPostgreSQLContainer getInstance() {
        if (container == null) {
            container = new SingletonPostgreSQLContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {}
}