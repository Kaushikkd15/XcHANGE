package com.Kaushik.Exchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class AppConfig {

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver"); // or com.mysql.cj.jdbc.Driver for MySQL
            dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
            dataSource.setUsername("postgres");
            dataSource.setPassword("Kaushikkd");
            return dataSource;
        }


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }



}