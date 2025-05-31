package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CareersPage {

    WebDriver driver;

    public CareersPage(WebDriver driver) {
        this.driver = driver;
    }

    private By locationsSection = By.xpath("//h3[contains(text(),'Our Locations')]");
    private By teamsSection = By.xpath("//h3[contains(text(),'Find your calling')]");
    private By lifeAtInsiderSection = By.xpath("//h2[contains(text(),'Life at Insider')]");

    public boolean isLocationsSectionDisplayed() {
        return driver.findElement(locationsSection).isDisplayed();
    }

    public boolean isTeamsSectionDisplayed() {
        return driver.findElement(teamsSection).isDisplayed();
    }

    public boolean isLifeAtInsiderSectionDisplayed() {
        return driver.findElement(lifeAtInsiderSection).isDisplayed();
    }

}
