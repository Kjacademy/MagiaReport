package com.zolyomi.magiareport.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DatasourceDI {

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        return databaseBuilder
            .setType(EmbeddedDatabaseType.HSQL)
            .build();
    }
}
