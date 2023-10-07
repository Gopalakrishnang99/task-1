package com.gopal.task.one.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class DbConfig {

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("DB/schema.sql"),new ClassPathResource("DB/data.sql")));
        initializer.setDatabasePopulator(populator);
        //initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("DB/schema.sql")));
        //initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("DB/data.sql")));
        return initializer;
    }
}
