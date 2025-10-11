package io.github.krishnaharshap.qa.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance("reports/ExtentReport.html");
        }
        return extent;
    }

    public static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter reporter = new ExtentSparkReporter(fileName);
        reporter.config().setReportName("Parabank UI Automation");
        reporter.config().setDocumentTitle("Automation Test Report");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        return extent;
    }
}
