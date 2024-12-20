package com.malikoyv.client;

import com.malikoyv.core.repositories.CatalogData;
import com.malikoyv.core.repositories.ICatalogData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BooksClientConfig {
    @Bean
    public IBooksClientUriBuilderProvider provider(){
        return new BooksClientUriBuilderProvider();
    }

    @Bean
    @Scope("prototype")
    public IBooksClient booksClient(IBooksClientUriBuilderProvider provider){
        return new BooksClient(provider);
    }
}
