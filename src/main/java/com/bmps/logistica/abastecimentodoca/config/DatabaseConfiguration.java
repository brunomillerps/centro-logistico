package com.bmps.logistica.abastecimentodoca.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.bmps.logistica.abastecimentodoca.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {
}