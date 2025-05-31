package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    private By logo = By.cssSelector("a.navbar-brand > img");
    private By cookieAcceptButton = By.cssSelector("#wt-cli-accept-all-btn");
    private By companyMenu = By.xpath("//a[contains(text(),'Company')]");
    private By careersLink = By.xpath("//a[contains(text(),'Careers')]");

    public boolean isLogoDisplayed() {
        return driver.findElement(logo).isDisplayed();
    }

    public void acceptCookiesIfPresent() {
        try {
            WebElement acceptBtn = wait.until(ExpectedConditions.presenceOfElementLocated(cookieAcceptButton));
            if (acceptBtn.isDisplayed()) {
                js.executeScript("arguments[0].click();", acceptBtn);
            }
        } catch (Exception ignored) {}
    }

    public void clickCompanyMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(companyMenu)).click();
    }

    public void clickCareersLink() {
        wait.until(ExpectedConditions.elementToBeClickable(careersLink)).click();
    }
}
