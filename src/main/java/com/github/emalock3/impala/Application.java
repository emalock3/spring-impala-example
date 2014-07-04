package com.github.emalock3.impala;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class Application {
    
    @Bean
    public Runnable queryRunner(JdbcTemplate jdbcTemplate, @Value("${impala.query}") String query) {
        return () -> {
            System.out.println("count result => " + jdbcTemplate.queryForObject(query, Long.class));
        };
    }
    
    public static void main(String... args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .showBanner(false)
                .web(false)
                .run(args);
        Runnable queryRunner = context.getBean("queryRunner", Runnable.class);
        queryRunner.run();
        context.stop();
    }
}
