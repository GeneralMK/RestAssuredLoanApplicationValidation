package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import utils.ExtentReportListener;

@CucumberOptions(
        features = "src/test/java/features",
        glue = {"steps","utils"},
        plugin = {"pretty", "utils.ExtentReportListener"},
        monochrome = true
)


public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public static void setup() {
        ExtentReportListener.setup();
    }

    @AfterClass
    public static void tearDown() {
        ExtentReportListener.flush();
    }

}
