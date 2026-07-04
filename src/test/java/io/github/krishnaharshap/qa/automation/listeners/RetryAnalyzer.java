package io.github.krishnaharshap.qa.automation.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retries a failed test up to MAX_RETRIES times before letting it fail for
 * good. ParabankLoginTest exercises a public third-party demo site
 * (parabank.parasoft.com), which occasionally has transient load/network
 * hiccups unrelated to the code under test; retrying absorbs that noise so
 * CI failures stay signal, not flake.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRIES = Integer.getInteger("testng.retry.max", 2);

    private int attempts = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (attempts < MAX_RETRIES) {
            attempts++;
            System.out.println("Retrying " + result.getName()
                    + " (attempt " + attempts + " of " + MAX_RETRIES + ")");
            return true;
        }
        return false;
    }
}
