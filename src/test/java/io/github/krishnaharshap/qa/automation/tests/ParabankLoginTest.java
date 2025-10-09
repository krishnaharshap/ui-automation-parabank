package io.github.krishnaharshap.qa.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ParabankLoginTest extends BaseTest {

    @Test
    public void testLoginPageLoadsSuccessfully() {
        page.navigate("https://parabank.parasoft.com/parabank/index.htm");
        String title = page.title();
        System.out.println("Page title: " + title);
        Assert.assertTrue(title.contains("ParaBank"), "Login page title should contain 'ParaBank'");
    }

    @Test
    public void testLoginErrorMessage() {
        page.navigate("https://parabank.parasoft.com/parabank/index.htm");
        page.fill("input[name='username']", "invalidUser");
        page.fill("input[name='password']", "invalidPass");
        page.click("input[value='Log In']");
        String errorText = page.textContent("#rightPanel p");
        Assert.assertTrue(errorText.contains("error"), "Expected login error message not found!");
    }
}
