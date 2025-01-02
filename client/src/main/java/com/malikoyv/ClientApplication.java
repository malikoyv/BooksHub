package com.malikoyv;

import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.AuthorSearchResponse;
import com.malikoyv.client.contract.EditionDto;
import com.malikoyv.client.contract.BookPagedResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

	@Autowired
	private IBooksClient bookClient;

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Override
	public void run(String... args) {
//		BookPagedResultDto books = bookClient.searchBooks("The Lord of the Rings", 1);
//		books.books().forEach(book -> System.out.println(book.title() + "\nAuthors:" + book.authors() + "\n"));
//
//		System.out.println("\nAuthors:");
//		AuthorSearchResponse authors = bookClient.searchAuthors("Tolkien");
//		authors.authors().forEach(author -> System.out.println(author.name()));
//
//		EditionDto edition = bookClient.getEditionDetails("OL27448W");
//		System.out.println("Edition Title: " + edition.title());
	}
}
