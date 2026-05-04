package io.github.krishnaharshap.qa.automation.tests;

import com.microsoft.playwright.*;
import io.github.krishnaharshap.qa.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ParabankLoginTest {

    private static final String BASE_URL = System.getProperty(
            "parabank.baseUrl",
            "https://parabank.parasoft.com/parabank/index.htm"
    );

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private LoginPage loginPage;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        boolean headless = Boolean.parseBoolean(System.getProperty("playwright.headless", "true"));
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
    }

    @BeforeMethod
    public void createPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1280, 720));
        page = context.newPage();
        page.setViewportSize(1280, 720);

        loginPage = new LoginPage(page);
    }

    @AfterMethod
    public void closePage(ITestResult result) {
        if (!result.isSuccess()) {
            captureScreenshot(result.getName());
        }
        if (context != null) context.close();
    }

    @AfterClass
    public void teardown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test(description = "Verify the ParaBank login form is available")
    public void testLoginFormIsDisplayed() {
        loginPage.navigateTo(BASE_URL);

        Assert.assertTrue(loginPage.isLoginFormVisible(), "Expected ParaBank login form to be visible.");
    }

    @Test(description = "Verify error message when credentials are missing")
    public void testMissingCredentialsErrorMessage() {
        loginPage.navigateTo(BASE_URL);
        loginPage.login("", "");

        Assert.assertTrue(loginPage.isLoginErrorVisible(), "Expected login error message not found.");
        Assert.assertTrue(
                loginPage.getLoginErrorMessage().toLowerCase().contains("username"),
                "Unexpected login error message: " + loginPage.getLoginErrorMessage()
        );
    }

    private void captureScreenshot(String testName) {
        try {
            Path screenshotDir = Paths.get("target", "screenshots");
            Files.createDirectories(screenshotDir);
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(screenshotDir.resolve(testName + ".png")));
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
