package io.github.krishnaharshap.qa.automation.tests;

import com.microsoft.playwright.Page;
import io.github.krishnaharshap.qa.automation.core.PlaywrightManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected Page page;

    @BeforeClass
    public void setUp() {
        page = PlaywrightManager.getPage();
    }

    @AfterClass
    public void tearDown() {
        PlaywrightManager.close();
    }
}
