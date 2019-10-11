package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page object for the page where the claimant is told they cannot apply if they live in Scotland.
 */
public class InScotlandPage extends BasePage {

    InScotlandPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/in-scotland";
    }

    @Override
    PageName getPageName() {
        return PageName.IN_SCOTLAND;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - You cannot apply if you live in Scotland";
    }

    public List<String> getAllBodyText() {
        return findAllBodyElementsOnPage().stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
