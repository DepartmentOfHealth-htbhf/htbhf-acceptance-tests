package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SelectAddressPage extends SubmittablePage {

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
        return findAllByCss("#address-results > option");
    }

    public WebElement getAddressNotListedLink () {
        return findByXpath("//a[contains(text(), \"My address is not listed\")]");
    }

    public WebElement getChangePostcodeLink () {
        return findByXpath("//a[contains(text(), \"Change\")]");
    }

    public void clickAddressNotListedLink () {
        WebElement addressNotListedLink = getAddressNotListedLink();
        addressNotListedLink.click();
    }

    public void clickChangePostcodeLink () {
        WebElement changePostcodeLink = getChangePostcodeLink();
        changePostcodeLink.click();
    }

    public WebElement getManualAddressLink () {
        return findByXpath("//a[contains(text(), \"Enter address manually\")]");
    }
}
