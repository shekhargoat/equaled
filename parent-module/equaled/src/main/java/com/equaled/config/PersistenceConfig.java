package com.callcomm.eserve.chatbot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.callcomm.eserve.chatbot.repository","com.callcomm.eserve.chatbot.customrepository"})
public class PersistenceConfig {

}
