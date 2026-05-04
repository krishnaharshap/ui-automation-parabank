package io.github.krishnaharshap.qa.automation.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginPage {

    private final Page page;

    // Locators
    private final String usernameInput = "input[name='username']";
    private final String passwordInput = "input[name='password']";
    private final String loginButton = "input[value='Log In']";
    private final String loginErrorMessage = "#rightPanel .error";

    public LoginPage(Page page) {
        this.page = page;
        page.setDefaultTimeout(60000); // 60s timeout for all actions
    }

    public void navigateTo(String url) {
        page.navigate(url);
        page.waitForLoadState();
    }

    public boolean isLoginFormVisible() {
        return page.locator(usernameInput).isVisible()
                && page.locator(passwordInput).isVisible()
                && page.locator(loginButton).isVisible();
    }

    public void login(String username, String password) {
        page.fill(usernameInput, username);
        page.fill(passwordInput, password);
        page.click(loginButton);
        page.waitForLoadState();
    }

    public boolean isLoginErrorVisible() {
        Locator errorMsg = page.locator(loginErrorMessage).first();
        try {
            errorMsg.waitFor(new Locator.WaitForOptions().setTimeout(60_000).setState(WaitForSelectorState.VISIBLE));
            return errorMsg.isVisible();
        } catch (PlaywrightException e) {
            return false;
        }
    }

    public String getLoginErrorMessage() {
        return page.locator(loginErrorMessage).first().textContent().trim();
    }
}
