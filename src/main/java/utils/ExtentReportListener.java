package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;


public class ExtentReportListener implements ConcurrentEventListener {
    private static ExtentReports extent;
    private static ExtentTest scenario;

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
    }

    private void handleTestStepStarted(TestStepStarted event) {
        if (scenario == null) {
            scenario = extent.createTest(event.getTestCase().getName());
        }
        scenario.log(com.aventstack.extentreports.Status.INFO, "Step Started: " + event.getTestStep().getCodeLocation());
    }

    private void handleTestStepFinished(TestStepFinished event) {
        switch (event.getResult().getStatus()) {
            case PASSED:
                scenario.log(com.aventstack.extentreports.Status.PASS, "Step Passed: " + event.getTestStep().getCodeLocation());
                break;
            case FAILED:
                scenario.log(com.aventstack.extentreports.Status.FAIL, "Step Failed: " + event.getTestStep().getCodeLocation());
                break;
            case SKIPPED:
                scenario.log(com.aventstack.extentreports.Status.SKIP, "Step Skipped: " + event.getTestStep().getCodeLocation());
                break;
            default:
                scenario.log(com.aventstack.extentreports.Status.INFO, "Step Finished: " + event.getTestStep().getCodeLocation());
                break;
        }
    }

    public static void setup() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("reports/cucumber-reports/extent-report.html");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Loan Application Test Execution Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Loan Application Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "Localhost");
        extent.setSystemInfo("Environment", "Test");
        extent.setSystemInfo("User Name", "Masixole Kondile");
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
