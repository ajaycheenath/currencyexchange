package com.af.bitcoin;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig
{
	
	@Value("#{T(java.time.Duration).parse('${http.connection.timeout}')}")
	private Duration connectionTimeout;
	
	@Value("#{T(java.time.Duration).parse('${http.read.timeout}')}")
	private Duration readTimeout;
	
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) 
    {
        return restTemplateBuilder
           .setConnectTimeout(connectionTimeout)
           .setReadTimeout(readTimeout)
           .build();
    }
}
