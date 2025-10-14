package io.github.krishnaharshap.qa.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;

public class HomePage {

    private final Page page;

    // Locators
    private final Locator welcomeHeading;
    private final Locator registerLink;

    public HomePage(Page page) {
        this.page = page;
        this.page.setDefaultTimeout(60000); // 60s default timeout

        // Initialize locators
        this.welcomeHeading = page.locator("css=h2.welcome");
        this.registerLink = page.locator("text=Register");
    }

    /** Click the Register link */
    public void clickRegister() {
        registerLink.waitFor(); // Waits until the element is visible
        registerLink.click();
    }

    /** Get the Welcome text safely */
    public String getWelcomeText() {
    // Wait until visible with timeout
    welcomeHeading.waitFor(new Locator.WaitForOptions().setTimeout(120_000).setState(WaitForSelectorState.VISIBLE));
    return welcomeHeading.textContent().trim();
}

    /** Utility: Check if welcome heading is visible */
public boolean isWelcomeVisible() {
    try {
        return welcomeHeading.isVisible();
    } catch (PlaywrightException e) {
        return false;
    }
}
}
