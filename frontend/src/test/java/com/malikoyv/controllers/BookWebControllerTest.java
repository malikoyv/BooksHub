package com.malikoyv.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class BookWebControllerTest {
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
    public void testCreateBook() {
        driver.get("http://localhost:8081/books/create");

        driver.findElement(By.id("title")).sendKeys("Test Book");
        driver.findElement(By.id("publishDate")).sendKeys("2023");
        driver.findElement(By.id("key")).sendKeys("test-book");

        WebElement authorsSelect = driver.findElement(By.id("authors"));
        authorsSelect.findElement(By.xpath("//option[text()='Test Author']")).click();

        WebElement subjectsSelect = driver.findElement(By.id("subjects"));
        subjectsSelect.findElement(By.xpath("//option[text()='Test Subject']")).click();

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertEquals("http://localhost:8081/books", driver.getCurrentUrl());
    }

    @Test
    public void testListBooks() {
        driver.get("http://localhost:8081/books");

        WebElement table = driver.findElement(By.tagName("table"));
        assertTrue(table.isDisplayed());

        assertFalse(driver.findElements(By.cssSelector("tbody tr")).isEmpty());
    }

    @Test
    public void testUpdateBook() throws InterruptedException {
        driver.get("http://localhost:8081/books");

        driver.findElement(By.cssSelector("a.btn-info")).click();

        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/update"));

        WebElement titleField = driver.findElement(By.id("title"));
        titleField.clear();
        titleField.sendKeys("Updated Book Title");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertTrue(driver.getCurrentUrl().startsWith("http://localhost:8081/books"));
    }

    @Test
    public void testDeleteBook() {
        driver.get("http://localhost:8081/books");

        driver.findElement(By.cssSelector("button.btn-danger")).click();


        assertEquals("http://localhost:8081/books", driver.getCurrentUrl());
    }
}
