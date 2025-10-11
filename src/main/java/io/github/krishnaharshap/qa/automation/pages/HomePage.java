package io.github.krishnaharshap.qa.automation.pages;

import com.microsoft.playwright.Page;

public class HomePage {
    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public void clickRegister() {
        page.click("text=Register");
    }

    public String getWelcomeText() {
        return page.textContent("css=h2.welcome");
    }
}
