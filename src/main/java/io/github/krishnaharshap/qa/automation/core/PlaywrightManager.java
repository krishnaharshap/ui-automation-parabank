package io.github.krishnaharshap.qa.automation.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightManager {
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    public static Page getPage() {
        if (page == null) {
            boolean headless = Boolean.parseBoolean(System.getProperty("playwright.headless", "true"));
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            context = browser.newContext();
            page = context.newPage();
        }
        return page;
    }

    public static void close() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}
