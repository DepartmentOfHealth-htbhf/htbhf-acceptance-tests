# htbhf-acceptance-tests
Acceptance tests for Apply for Healthy Start application

These tests are downloaded and run by the htbhf-applicant-web-ui project 
(which specifies the version of these tests to run in `test_versions.properties`). 
The acceptance tests are run as part of the CI build for htbhf-applicant-web-ui and the compatibility tests are run during the CD pipeline.

## Running the acceptance tests against a local instance of htbhf-applicant-web-ui
When developing new features you can run against your local copy of htbhf-applicant-web-ui by running the 
`run_tests_with_local_feature_toggles.sh` script. The tests require that applicant-web-ui is running (`npm start`), 
and that the session details app is also running (`npm run test:session-details`). 
All other endpoints (claimant service and os-places) are mocked out by the tests.

When pushing your changes to GitHub, tell circleci which branch of htbhf-applicant-web-ui it should test against 
by setting the `WEB_UI_BRANCH` environment property in `.circleci/config.yml`. This property is only used when running 
the CI build of htbhf-acceptance-tests, so it is safe to include in a PR. 
If creating a PR that runs against the latest build of htbhf-applicant-web-ui, set `WEB_UI_BRANCH` to an empty string.

## Running the Compatibility Tests from Local
To run the BrowserStack compatibility tests from your local machine, you simply need to set the following environment variables:

```
BROWSER_STACK_USER
BROWSER_STACK_KEY
APP_BASE_URL
SESSION_DETAILS_URL
```

and then run `./gradlew compatibility`.
