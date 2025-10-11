package io.github.krishnaharshap.qa.automation.listeners;

import io.github.krishnaharshap.qa.automation.utils.ExtentManager;
import org.testng.*;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }
}
