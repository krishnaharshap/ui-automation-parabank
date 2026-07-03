package io.github.krishnaharshap.qa.automation.base;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    @Parameters("browser")
    public void setUp(@Optional("chromium") String browserName) {
        boolean headless = Boolean.parseBoolean(System.getProperty("playwright.headless", "true"));
        playwright = Playwright.create();
        browser = switch (browserName.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "webkit" -> playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            default -> playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        };
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterClass
    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
