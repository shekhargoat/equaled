package com.equaled.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.equaled.repository","com.equaled.customrepository"})
public class PersistenceConfig {

}
