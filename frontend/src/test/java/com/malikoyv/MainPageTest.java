package com.malikoyv;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Yehor\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testHeroSection() {
        driver.get("http://localhost:8081/");

        WebElement hero = driver.findElement(By.className("hero"));
        assertNotNull(hero, "Hero section should exist");
        assertTrue(hero.getText().contains("Welcome to BooksHub"), "Hero section should contain welcome text");

        WebElement exploreBooksButton = hero.findElement(By.tagName("a"));
        assertNotNull(exploreBooksButton, "Explore Books button should exist");
        assertEquals("http://localhost:8081/books", exploreBooksButton.getAttribute("href"), "Explore Books button should link to /books");
    }

    @Test
    public void testCardsSection() {
        driver.get("http://localhost:8081/");

        List<WebElement> cards = driver.findElements(By.className("card"));
        assertEquals(3, cards.size(), "There should be 3 cards on the dashboard");

        WebElement booksCard = cards.get(0);
        assertTrue(booksCard.getText().contains("Books"), "First card should be about Books");
        WebElement booksLink = booksCard.findElement(By.tagName("a"));
        assertEquals("http://localhost:8081/books", booksLink.getAttribute("href"), "Books card should link to /books");

        WebElement authorsCard = cards.get(1);
        assertTrue(authorsCard.getText().contains("Authors"), "Second card should be about Authors");
        WebElement authorsLink = authorsCard.findElement(By.tagName("a"));
        assertEquals("http://localhost:8081/authors", authorsLink.getAttribute("href"), "Authors card should link to /authors");

        WebElement subjectsCard = cards.get(2);
        assertTrue(subjectsCard.getText().contains("Subjects"), "Third card should be about Subjects");
        WebElement subjectsLink = subjectsCard.findElement(By.tagName("a"));
        assertEquals("http://localhost:8081/subjects", subjectsLink.getAttribute("href"), "Subjects card should link to /subjects");
    }

    @Test
    public void testFooter() {
        driver.get("http://localhost:8081/");

        WebElement footer = driver.findElement(By.tagName("footer"));
        assertNotNull(footer, "Footer should exist");
        assertTrue(footer.getText().contains("BooksHub © 2025"), "Footer should contain 'BooksHub © 2025' text");

        WebElement developerLink = footer.findElement(By.tagName("a"));
        assertNotNull(developerLink, "Developer link should exist");
        assertEquals("https://github.com/malikoyv", developerLink.getAttribute("href"), "Developer link should point to the GitHub page");
    }
}
