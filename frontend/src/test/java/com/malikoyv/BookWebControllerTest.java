package com.malikoyv;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookWebControllerTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Yehor\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8081/books");
    }

    @Test
    public void testCreateBook() {
        driver.findElement(By.linkText("Create New Book")).click();

        driver.findElement(By.id("title")).sendKeys("Test Book Title");
        driver.findElement(By.id("publishDate")).sendKeys("2023");
        driver.findElement(By.id("key")).sendKeys("test-book-key");

        Select authorsSelect = new Select(driver.findElement(By.id("authors")));
        authorsSelect.selectByIndex(0); // Assuming at least one author exists

        // Select subjects
        Select subjectsSelect = new Select(driver.findElement(By.id("subjects")));
        subjectsSelect.selectByIndex(0); // Assuming at least one subject exists

        // Submit the form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify the redirect to the books list and the new book appears
        WebElement bookTitle = driver.findElement(By.xpath("//td[text()='Test Book Title']"));
        assertNotNull(bookTitle, "Newly created book should appear in the list.");
    }

    @Test
    public void testUpdateBook() {
        // Navigate to the update form of the created book
        driver.findElement(By.xpath("//td[text()='Test Book Title']/following-sibling::td/a[text()='Update']")).click();

        // Modify the title
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.clear();
        titleField.sendKeys("Updated Test Book Title");

        // Submit the form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify the update
        WebElement updatedBookTitle = driver.findElement(By.xpath("//td[text()='Updated Test Book Title']"));
        assertNotNull(updatedBookTitle, "Updated book title should appear in the list.");
    }

    @Test
    public void testDeleteBook() {
        // Delete the book
        driver.findElement(By.xpath("//td[text()='Updated Test Book Title']/following-sibling::td/form/button")).click();

        // Verify the book is no longer in the list
        boolean isBookPresent = driver.findElements(By.xpath("//td[text()='Updated Test Book Title']")).isEmpty();
        assertTrue(isBookPresent, "Deleted book should no longer appear in the list.");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
