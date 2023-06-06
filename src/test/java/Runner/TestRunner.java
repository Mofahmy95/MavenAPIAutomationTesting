package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
       // features = "src/test/Tests/GetAllUsers.feature",
       // features = "src/test/Tests/EndToEndScenario.feature",
        features = "src/test/Tests",
        tags = "@Sanity",
        glue = ""
)

public class TestRunner {
}