package uk.gov.dhsc.htbhf.browserstack;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import uk.gov.dhsc.htbhf.TestResult;
import uk.gov.dhsc.htbhf.TestResultHandler;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Uploads the result to BrowserStack after the test is completed otherwise tests that fail are marked as passing.
 */
@Slf4j
public class BrowserStackResultUploader implements TestResultHandler {

    private final String browserStackUser;
    private final String browserStackKey;

    public BrowserStackResultUploader(String browserStackUser, String browserStackKey) {
        this.browserStackUser = browserStackUser;
        this.browserStackKey = browserStackKey;
    }

    @Override
    public void handleResults(TestResult testResult, String sessionId) {
        log.info("Uploading results for test with sessionId: {}", sessionId);
        try {
            String url = buildUrlForSession(sessionId);
            URI uri = new URI(url);
            HttpPut putRequest = buildPutRequest(testResult, uri);

            HttpClientBuilder.create().build().execute(putRequest);
        } catch (Exception e) {
            log.error("Unexpected Exception caught trying to post results to BrowserStack", e);
        }
    }

    private HttpPut buildPutRequest(TestResult testResult, URI uri) throws UnsupportedEncodingException {
        HttpPut putRequest = new HttpPut(uri);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add((new BasicNameValuePair("status", testResult.getStatus())));
        nameValuePairs.add((new BasicNameValuePair("name", testResult.getName())));
        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        return putRequest;
    }

    private String buildUrlForSession(String sessionId) {
        return String.format("https://%s:%s@api.browserstack.com/automate/sessions/%s.json", browserStackUser, browserStackKey, sessionId);
    }
}
