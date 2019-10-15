package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SelectAddressPage extends SubmittablePage {

    private static final String ADDRESS_RESULTS_OPTION_CSS = "#address-results > option";

    public SelectAddressPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/select-address";
    }

    @Override
    PageName getPageName() {
        return PageName.SELECT_ADDRESS;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - What’s your address?";
    }

    public WebElement getAddressNotFoundElement () {
        return findByXpath("//h2[contains(text(), \"We cannot find that address\")]");
    }

    public WebElement getPostcodeLookupNotWorkingElement () {
        return findByXpath("//h1[contains(text(), \"There’s a problem with the postcode finder\")]");
    }

    public List<WebElement> getAddressOptions () {
        return findAllByCss(ADDRESS_RESULTS_OPTION_CSS);
    }

    private WebElement getAddressNotListedLink() {
        return findByXpath("//a[contains(text(), \"My address is not listed\")]");
    }

    public String getAddressNotListedLinkHref() {
        return getHrefForElement(getAddressNotListedLink());
    }

    private WebElement getChangePostcodeLink() {
        return findByXpath("//a[contains(text(), \"Change\")]");
    }

    public String getChangePostcodeLinkHref() {
        return getHrefForElement(getChangePostcodeLink());
    }

    public void clickAddressNotListedLink () {
        WebElement addressNotListedLink = getAddressNotListedLink();
        addressNotListedLink.click();
    }

    public void clickChangePostcodeLink () {
        WebElement changePostcodeLink = getChangePostcodeLink();
        changePostcodeLink.click();
    }

    public String getManualAddressLinkHref() {
        WebElement manualAddressLink = findByXpath("//a[contains(text(), \"Enter address manually\")]");
        return getHrefForElement(manualAddressLink);
    }

    public void selectFirstAddress() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ADDRESS_RESULTS_OPTION_CSS)));
        List<WebElement> addressOptions = getAddressOptions();
        WebElement option = addressOptions.get(0);
        option.click();
    }
}
