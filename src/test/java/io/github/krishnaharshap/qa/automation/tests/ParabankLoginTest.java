package io.github.krishnaharshap.qa.automation.tests;

import com.microsoft.playwright.*;
import io.github.krishnaharshap.qa.automation.pages.HomePage;
import io.github.krishnaharshap.qa.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.nio.file.Paths;

public class ParabankLoginTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        page.setViewportSize(1280, 720);

        loginPage = new LoginPage(page);
        homePage = new HomePage(page);
    }

    @AfterClass
    public void teardown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeMethod
    public void navigateToLogin() {
        page.navigate("https://parabank.parasoft.com/parabank/index.htm");
    }

    @Test(description = "Verify homepage welcome text after login")
    public void testHomePageWelcome() {
        try {
            loginPage.login("validUsername", "validPassword");

            // Wait for home page welcome header to be visible
            String welcomeText = homePage.getWelcomeText();
            Assert.assertTrue(welcomeText.contains("Welcome"), "Homepage welcome text not found!");
        } catch (Exception e) {
            captureScreenshot("testHomePageWelcome");
            throw e;
        }
    }

    @Test(description = "Verify error message on invalid login")
    public void testLoginErrorMessage() {
        try {
            loginPage.login("invalidUser", "invalidPass");

            boolean isErrorVisible = loginPage.isLoginErrorVisible();
            Assert.assertTrue(isErrorVisible, "Expected login error message not found!");
        } catch (Exception e) {
            captureScreenshot("testLoginErrorMessage");
            throw e;
        }
    }

    /** Utility method to capture screenshot on failure */
    private void captureScreenshot(String testName) {
        try {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("screenshots/" + testName + ".png")));
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
