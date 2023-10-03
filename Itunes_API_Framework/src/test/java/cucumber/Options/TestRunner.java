package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/features",
				plugin = {"pretty","html:target/report/cucumber_html_reports.html","json:target/report/cucumber_json_report.json"},
				glue= {"stepDefinations"},tags = "@Regression")
public class TestRunner {

}