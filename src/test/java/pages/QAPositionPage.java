package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class QAPositionPage {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public QAPositionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    private By cookieAcceptButton = By.cssSelector("#wt-cli-accept-all-btn");
    private By seeAllQAJobsButton = By.cssSelector("#page-head > div > div > div.col-12.col-lg-7.order-2.order-lg-1 > div > div > a");
    private By locationDropdownArrow = By.cssSelector("#top-filter-form > div:nth-child(1) > span > span.selection > span > span.select2-selection__arrow > b");
    private By firstViewRoleBtn = By.cssSelector("#jobs-list > div:nth-child(1) > div > a");
    private By firstJobCard = By.cssSelector("#jobs-list > div:nth-child(1) > div");

    public void acceptCookiesIfPresent() {
        try {
            WebElement acceptBtn = wait.until(ExpectedConditions.presenceOfElementLocated(cookieAcceptButton));
            if (acceptBtn.isDisplayed()) {
                js.executeScript("arguments[0].click();", acceptBtn);
            }
        } catch (Exception ignored) {
        }
    }

    public void clickSeeAllQAJobs() {
        driver.findElement(seeAllQAJobsButton).click();
        sleep(5000);
    }

    public void selectLocationIstanbul() {
        try {
            WebElement arrow = wait.until(ExpectedConditions.elementToBeClickable(locationDropdownArrow));

            // Aç – kapat işlemini 3 kez 3 saniye arayla yap
            for (int i = 0; i < 3; i++) {
                arrow.click();
                sleep(3000);
            }

            // İstanbul seçeneğini bekle ve tıkla
            WebElement istanbulOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//li[contains(text(), 'Istanbul, Turkiye')]")));
            istanbulOption.click();

            sleep(5000); // Sonuçlar yüklensin
        } catch (Exception e) {
            throw new RuntimeException("Lokasyon seçiminde hata oluştu: " + e.getMessage());
        }
    }

    public void hoverAndClickFirstViewRoleButton() {
        WebElement card = wait.until(ExpectedConditions.presenceOfElementLocated(firstJobCard));

        Actions actions = new Actions(driver);
        actions.moveToElement(card).perform();  // mouse-over işlemi

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(firstViewRoleBtn));
        js.executeScript("arguments[0].click();", button);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}
