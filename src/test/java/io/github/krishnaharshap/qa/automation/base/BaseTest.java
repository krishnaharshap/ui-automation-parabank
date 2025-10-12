package io.github.krishnaharshap.qa.automation.base;

import com.microsoft.playwright.*;
import io.github.krishnaharshap.qa.automation.utils.ExtentManager;
import org.testng.annotations.*;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    @Parameters("browser")
    public void setUp(@Optional("chromium") String browserName) {
        playwright = Playwright.create();
        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(true));
                break;
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        }
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
