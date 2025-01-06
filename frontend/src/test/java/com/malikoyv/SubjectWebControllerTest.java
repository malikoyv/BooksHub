package com.malikoyv;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubjectWebControllerTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Yehor\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testListSubjects() {
        driver.get("http://localhost:8081/subjects");

        assertTrue(driver.getTitle().contains("Subjects"));

        WebElement createButton = driver.findElement(By.cssSelector("a.btn-primary"));
        assertEquals("Create New Subject", createButton.getText());
    }

    @Test
    public void testCreateSubject() {
        driver.get("http://localhost:8081/subjects/create");

        assertTrue(driver.getTitle().contains("Create Subject"));

        WebElement nameField = driver.findElement(By.id("name"));
        nameField.sendKeys("Test Subject");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.urlToBe("http://localhost:8081/subjects"));

        WebElement createdSubject = driver.findElement(By.xpath("//td[text()='Test Subject']"));
        assertTrue(createdSubject.isDisplayed());
    }

    @Test
    public void testDeleteSubject() {
        driver.get("http://localhost:8081/subjects");

        WebElement deleteButton = driver.findElement(By.cssSelector("a.btn-danger"));
        deleteButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.urlToBe("http://localhost:8081/subjects"));

        boolean subjectDeleted = driver.findElements(By.xpath("//td[text()='Test Subject']")).isEmpty();
        assertTrue(subjectDeleted);
    }
}
