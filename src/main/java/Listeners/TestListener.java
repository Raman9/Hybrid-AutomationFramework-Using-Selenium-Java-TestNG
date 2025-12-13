package Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Reporting.ExtentManager;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    // Initialize the report
    private static ExtentReports extent = ExtentManager.createInstance();
    // ThreadLocal makes this thread-safe for parallel execution
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        
        test.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        
        test.get().log(Status.FAIL, "Test Failed");
        test.get().fail(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        
        extent.flush();
    }
}
