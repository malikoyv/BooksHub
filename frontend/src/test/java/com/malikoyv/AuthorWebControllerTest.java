package com.malikoyv;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorWebControllerTest {
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
    public void testCreateAuthor() {
        driver.get("http://localhost:8081/authors/create");

        driver.findElement(By.id("name")).sendKeys("Test Author");
        driver.findElement(By.id("key")).sendKeys("test-author");
        driver.findElement(By.id("alternateNames")).sendKeys("Alias1, Alias2");
        driver.findElement(By.id("birthDate")).sendKeys("1990-01-01");
        driver.findElement(By.id("deathDate")).sendKeys("2020-12-31");
        driver.findElement(By.id("topSubjects")).sendKeys("Subject1, Subject2");
        driver.findElement(By.id("topWork")).sendKeys("Famous Work");
        driver.findElement(By.id("workCount")).sendKeys("10");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertEquals("http://localhost:8081/authors", driver.getCurrentUrl());
    }

    @Test
    public void testListAuthors() {
        driver.get("http://localhost:8081/authors");

        WebElement table = driver.findElement(By.tagName("table"));
        assertTrue(table.isDisplayed());

        assertFalse(driver.findElements(By.cssSelector("tbody tr")).isEmpty());
    }

    @Test
    public void testUpdateAuthor() {
        driver.get("http://localhost:8081/authors");

        driver.findElement(By.cssSelector("a.btn-info")).click();

        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/update"));

        WebElement nameField = driver.findElement(By.id("name"));
        nameField.clear();
        nameField.sendKeys("Updated Author Name");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertEquals("http://localhost:8081/authors/agatha-christie-45831783", driver.getCurrentUrl());
    }

    @Test
    public void testDeleteAuthor() {
        driver.get("http://localhost:8081/authors");

        WebElement deleteButton = driver.findElement(By.cssSelector("a.btn-danger"));
        String deleteUrl = deleteButton.getAttribute("href");
        assert deleteUrl != null;
        driver.get(deleteUrl);

        assertEquals("http://localhost:8081/authors/agatha-christie-45831783/delete", driver.getCurrentUrl());
    }
}
