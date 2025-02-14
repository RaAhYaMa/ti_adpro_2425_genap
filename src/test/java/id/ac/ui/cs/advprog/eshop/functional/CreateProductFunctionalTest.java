package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    private ChromeDriver driver;

    @BeforeEach
    void setupTest(ChromeDriver driver) {
        this.driver = driver;
        this.driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        this.driver.quit();
    }

    @Test
    void testCreateProduct() {
        this.driver.get("http://localhost:" + this.serverPort + "/product/create");

        this.driver.findElement(By.name("productName")).sendKeys("Sampo Cap Bambang");
        this.driver.findElement(By.name("productQuantity")).sendKeys("100");
        this.driver.findElement(By.xpath("//button[@type='submit']")).click();

        assertEquals("http://localhost:" + this.serverPort + "/product/list", this.driver.getCurrentUrl());
        assertEquals("Sampo Cap Bambang", this.driver.findElement(By.xpath("//table/tbody/tr/td[1]")).getText());
        assertEquals("100", this.driver.findElement(By.xpath("//table/tbody/tr/td[2]")).getText());
    }
}