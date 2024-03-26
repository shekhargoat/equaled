package com.equaled.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.callcomm.eserve.common.graph.dao"})
public class PersistenceConfig1 {
}
