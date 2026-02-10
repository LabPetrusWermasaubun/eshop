package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class HomePageFunctionalTest {

    /**
     * Port nomor yang diberikan ke aplikasi selama eksekusi test.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * Base URL untuk testing. Default ke http://localhost.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        // Exercise: Membuka URL aplikasi
        driver.get(baseUrl);
        String pageTitle = driver.getTitle();

        // Verify: Mengecek apakah title sesuai (ADV Shop)
        assertEquals("ADV Shop", pageTitle);
    }

    @Test
    void welcomeMessage_homePage_isCorrect(ChromeDriver driver) throws Exception {
        // Exercise: Mencari elemen <h3> dan mengambil teksnya
        driver.get(baseUrl);
        String welcomeMessage = driver.findElement(By.tagName("h3"))
                .getText();

        // Verify: Mengecek apakah pesan selamat datang sesuai (Welcome)
        assertEquals("Welcome", welcomeMessage);
    }
}