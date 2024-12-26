package com.malikoyv.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BooksClientConfig {
    @Bean
    public IBooksClientUriBuilderProvider provider(){
        return new BooksClientUriBuilderProvider();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Scope("prototype")
    public IBooksClient booksClient(IBooksClientUriBuilderProvider provider, RestTemplate restTemplate){
        return new BooksClient(provider, restTemplate);
    }
}
