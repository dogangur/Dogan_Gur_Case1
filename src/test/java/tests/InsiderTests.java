package tests;

import base.BaseTest;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.QAPositionPage;
import pages.CareersPage;

public class InsiderTests extends BaseTest {

    @Test
    public void verifyHomePageIsOpened() {
        driver.get("https://useinsider.com/");
        HomePage homePage = new HomePage(driver);
        homePage.acceptCookiesIfPresent();  // Çerez varsa kapat
        Assert.assertTrue(homePage.isLogoDisplayed(), "Home page logo is not displayed!");
    }

    @Test
    public void verifyCareerPageSectionsAreVisible() throws InterruptedException {
        driver.get("https://useinsider.com/");
        HomePage homePage = new HomePage(driver);
        homePage.acceptCookiesIfPresent();
        homePage.clickCompanyMenu();
        homePage.clickCareersLink();

        CareersPage careersPage = new CareersPage(driver);

        Assert.assertTrue(careersPage.isLocationsSectionDisplayed(), "'Our Locations' section not visible!");
        Assert.assertTrue(careersPage.isTeamsSectionDisplayed(), "'Find your calling' section not visible!");
        Assert.assertTrue(careersPage.isLifeAtInsiderSectionDisplayed(), "'Life at Insider' section not visible!");

        scrollToBottomGradually(); // Sayfa sonuna kadar yavaşça kaydır
    }

    @Test
    public void verifyQAJobsWithIstanbulFilter() throws InterruptedException {
        closeBrowser = false;

        driver.get("https://useinsider.com/careers/quality-assurance/");

        QAPositionPage qaPage = new QAPositionPage(driver);
        qaPage.acceptCookiesIfPresent();
        qaPage.clickSeeAllQAJobs();
        qaPage.selectLocationIstanbul();
        scrollOneStepDown();
        qaPage.hoverAndClickFirstViewRoleButton();

        Thread.sleep(10000);
    }

    public void scrollOneStepDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 300);");
    }

    public void scrollToBottomGradually() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");
        long currentPosition = 0;

        while (currentPosition + 400 < lastHeight) {
            js.executeScript("window.scrollBy(0, 300);");
            currentPosition += 300;
            Thread.sleep(1000); // Her adım arasında bekleme
            lastHeight = (long) js.executeScript("return document.body.scrollHeight");
        }
    }
}
