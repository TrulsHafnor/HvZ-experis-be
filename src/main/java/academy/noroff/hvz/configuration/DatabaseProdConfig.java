package academy.noroff.hvz.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class DatabaseProdConfig {
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://ec2-54-77-40-202.eu-west-1.compute.amazonaws.com:5432/d3orl1u7576pir");
        dataSourceBuilder.username("qbgkuxkcfhgvli");
        dataSourceBuilder.password("a04da81841d036cd9fb5d3292602f3d1b42b748177bed2728d68092e06dac3ba");
        return dataSourceBuilder.build();
    }
}
