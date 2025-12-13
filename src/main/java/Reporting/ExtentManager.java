package Reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports createInstance() {
        
        String fileName = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
        
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
        
        // Configuration (Look and Feel)
        htmlReporter.config().setTheme(Theme.STANDARD); // or Theme.DARK
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }
}