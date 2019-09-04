# htbhf-acceptance-tests
Acceptance tests for Apply for Healthy Start application

## Running the Compatibility Tests from Local
To run the BrowserStack compatibility tests from your local machine, you simply need to set the following environment variables:

```
BROWSER_STACK_USER
BROWSER_STACK_KEY
APP_BASE
SESSION_DETAILS_URL
```

and then run `./gradlew compatibility`.
