# htbhf-acceptance-tests
Contains acceptance tests and compatibility tests for Apply for Healthy Start application using Cucumber and Selenium Webdriver to run Gherkin feature files. 
Note that the compatibility test feature file (`/src/test/resources/features/compatibility/apply-success.feature`) is also run as a part of the acceptance
tests to make sure that there is no error in this file before we run the compatibility tests.

## Building this project in Circle CI
The build for this project by default downloads and uses the latest release of the htbhf-applicant-web-ui project to run the tests against. The 
script `prepare_for_tests.sh` is used by Circle CI to download the appropriate version. The version used can be overridden by setting the environment variable
`WEB_UI_BRANCH` which is useful when testing a specific branch of the web-ui. This can be done for the Circle CI build in the `.circleci/config.yml` file in 
the `jobs/build/environment` section (NOTE: This value needs to be reset back to blank once the branch is deleted otherwise subsequent builds will fail. The
build itself runs the following scripts in this order:

 - `ci_scripts/prepare_for_tests.sh` - downloads and runs the appropriate version of web-ui and the session-details application
 - `ci_scripts/download_chromedriver.sh` - downloads the appropriate version of chromedriver into the project bin directory (see below for more specific details on this). 
 - `/run_tests_with_local_feature_togges.sh` - loads the feature toggles required from an environment variable and runs gradle to run the tests

## Build for the htbhf-applicant-web-ui project
These tests are downloaded and run by the htbhf-applicant-web-ui project, the specific version is defined in `test_versions.properties` in that project. This
means that if a new release of the acceptance is created, you need to change the version number in that file for the new changes to be used in the web-ui 
project's tests. The acceptance tests are run as part of the CI build for htbhf-applicant-web-ui as a part of the npm test command after the unit tests 
and accessibility tests have passed.

## Running the acceptance tests against a local instance of htbhf-applicant-web-ui
When developing new features you can run against your local copy of htbhf-applicant-web-ui by running the 
`run_tests_with_local_feature_toggles.sh` script. The tests require that applicant-web-ui is running (`npm start`), 
and that the session details app is also running (`npm run test:session-details`). 
All other endpoints (claimant service and os-places) are mocked out by the tests.

## Running a single feature file
If you want to run a single feature file, simple modify the list of features specified in the `RunAcceptanceTests` class in the `@CucumberOptions` annotation.
For example, if you wanted to just run the `decision.feature` file for local testing you would have:

```
@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty",
        features = "src/test/resources/features/acceptance/decision.feature",
        strict = true
)
public class RunAcceptanceTests {

}
```
Once this has been done, you will then need to run `RunAcceptanceTests` as a unit test in IntelliJ for this to take effect. You will also need to change
the run configuration of that test as such, making sure to change to your local working directory as applicable:

 **VM Options**: 
 `-ea
-Dwebdriver.chrome.driver=/Users/mikesmith/dev/ats/bin/chromedriver`

 **Environment variables**:
 `FEATURE_TOGGLES={   "ADDRESS_LOOKUP_ENABLED": true } `

## Running Specific Scenarios
If you want to be even more specific with the tests you run and want to run a set of specific tests, you can use tags in the feature files. For example, if you
were using a specific tag called `@LocalTests`, you would modify the scenario as such:

```
  @LocalTests
  Scenario: Valid application details can be checked
    Given I am on the first page of the application
    When I complete the application with valid details for a pregnant woman
    Then I am shown the check answers page with correct page content
    And I accept the terms and conditions and submit my application
    And I am shown a successful decision page
    And my entitlement is £9.30 per week with a first payment of £37.20
```
and then modify `RunAcceptanceTests` as such:

```
@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty",
        features = "src/test/resources/features",
        tags = "@LocalTests",
        strict = true
)
public class RunAcceptanceTests {

}
```
Again you will need to make sure that your run profile in IntelliJ also has the correct VM Options and Environment Variables as listed above to do this.

## Downloading and using chromedriver

To run the tests locally you will need to have a local copy of chromedriver. This can be downloaded from https://chromedriver.chromium.org/downloads and should be placed in the directory listed in your VM Options.
If you want to run the test from gradle, then this should be added to the `/bin` directory in your gradle project directory.

## Running the Compatibility Tests from Local
To run the BrowserStack compatibility tests from your local machine, you simply need to set the following environment variables:

```
BROWSER_STACK_USER
BROWSER_STACK_KEY
APP_BASE_URL
SESSION_DETAILS_URL
```

and then run `./gradlew compatibility`.

## Adding extra compatibility tests

The compatibility tests all run a single feature file (`apply-success.feature`) but each test runs that feature file in a separate thread against a different combination of operating system or mobile device
against a specified browser. This BrowserStack configuration is specified in the properties files found in the `src/test/resources/browserstack` directory. The Compatibility test framework loads and runs 
every file in that directory as its own compatibility test, so if you want to run a new compatibility test, all you need to do is add a new file of the same format to that directory. To find the BrowserStack
compatibility features for your test, consult this page to get the BrowserStack capabilities to add to your file: https://www.browserstack.com/automate/capabilities

## Modifying the number of threads or retries for compatibility tests

The current setup runs a total of 5 threads in parallel, each one running a single Compatibility Test. This is the optimal amount of threads for the current BrowserStack plan we are on as it only allows 5
tests to be run concurrently, so modifying this value is not recommended. For reference, the value can be found in the `BrowserStackLauncher.java` file under the constant `MAX_THREADS`.

Each test also has a retry mechanism built into it should there be a failure in the test. Browser testing such as this is not 100% reliable (especially on iOS or Mac operating systems) so retries are a necessary evil.
At the moment we have configured a maximum of 4 retries before a test will be considered a failure, which is plenty from current experience. Should this value need to be changed, it can also be found in 
`BrowserStackLauncher.java` under the constant `MAX_RETRY_ATTEMPTS`. Obviously the retry mechanism will only use those retries if necessary.

## Compatibility test report

A basic HTML report output is generated at the end of the process. This file is built up from a Velocity template found in `src/test/resources/velocity/results-summary.vm`. The report is output to 
`build/reports/compatibility-report` which is configured in `BrowserStackLauncher.java` again. When run as a part of the CI process, this report is uploaded to GitHub pages and made available
at the following address: https://departmentofhealth-htbhf.github.io/htbhf-continous-delivery/docs/compatibility-report/index.html
