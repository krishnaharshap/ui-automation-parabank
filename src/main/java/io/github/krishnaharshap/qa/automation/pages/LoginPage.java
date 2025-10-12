package io.github.krishnaharshap.qa.automation.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    // Locators
    private final String usernameInput = "input[name='username']";
    private final String passwordInput = "input[name='password']";
    private final String loginButton = "input[value='Log In']";
    private final String loginErrorMessage = "div#errorPanel li";

    public LoginPage(Page page) {
        this.page = page;
        page.setDefaultTimeout(60000); // 60s timeout for all actions
    }

    /** Perform login with given username and password */
    public void login(String username, String password) {
        page.fill(usernameInput, username);
        page.fill(passwordInput, password);
        page.click(loginButton);

        // Optional: wait for either homepage welcome or login error message
        page.waitForLoadState();
    }

    /** Check if login error message is visible */
    public boolean isLoginErrorVisible() {
        return page.isVisible(loginErrorMessage);
    }
}
