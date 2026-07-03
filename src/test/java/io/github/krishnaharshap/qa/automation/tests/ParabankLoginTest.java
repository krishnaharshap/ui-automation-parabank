package io.github.krishnaharshap.qa.automation.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import io.github.krishnaharshap.qa.automation.base.BaseTest;
import io.github.krishnaharshap.qa.automation.listeners.RetryAnalyzer;
import io.github.krishnaharshap.qa.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ParabankLoginTest extends BaseTest {

    private static final String BASE_URL = System.getProperty(
            "parabank.baseUrl",
            "https://parabank.parasoft.com/parabank/index.htm"
    );

    private LoginPage loginPage;

    @BeforeMethod
    public void createPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1280, 720));
        page = context.newPage();

        loginPage = new LoginPage(page);
    }

    @AfterMethod
    public void closePage(ITestResult result) {
        if (!result.isSuccess()) {
            captureScreenshot(result.getName());
        }
        if (context != null) context.close();
    }

    @Test(description = "Verify the ParaBank login form is available", retryAnalyzer = RetryAnalyzer.class)
    public void testLoginFormIsDisplayed() {
        loginPage.navigateTo(BASE_URL);

        Assert.assertTrue(loginPage.isLoginFormVisible(), "Expected ParaBank login form to be visible.");
    }

    @Test(description = "Verify error message when credentials are missing", retryAnalyzer = RetryAnalyzer.class)
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
