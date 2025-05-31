package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class BasePage {
    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        handleCookieAndNotificationConsent();
    }

    public void handleCookieAndNotificationConsent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement cookieBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#wt-cli-accept-all-btn")));
            cookieBtn.click();
        } catch (Exception ignored) {}

        try {
            WebElement allowNotifBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'İzin ver')]")));
            allowNotifBtn.click();
        } catch (Exception ignored) {}
    }
}
