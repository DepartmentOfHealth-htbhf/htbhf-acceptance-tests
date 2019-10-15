package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Store for all the pages in the application so we can get and navigate to them easily.
 */
public class Pages {

    private WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private String baseUrl;
    private List<BasePage> pages = new ArrayList<>();
    private GenericPage genericPage;

    public Pages(WebDriver webDriver, WebDriverWait webDriverWait, String baseUrl) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
        this.baseUrl = baseUrl;
        initPages();
        //We specifically don't add the GenericPage to the List as it doesn't have a PageName
        this.genericPage = new GenericPage(webDriver, baseUrl, webDriverWait);
    }

    private void initPages() {
        pages.add(new AreYouPregnantPage(webDriver, baseUrl, webDriverWait));
        pages.add(new CheckAnswersPage(webDriver, baseUrl, webDriverWait));
        pages.add(new ChildDateOfBirthPage(webDriver, baseUrl, webDriverWait));
        pages.add(new ConfirmationPage(webDriver, baseUrl, webDriverWait));
        pages.add(new ConfirmUpdatedPage(webDriver, baseUrl, webDriverWait));
        pages.add(new DateOfBirthPage(webDriver, baseUrl, webDriverWait));
        pages.add(new DoYouHaveChildrenPage(webDriver, baseUrl, webDriverWait));
        pages.add(new EmailAddressPage(webDriver, baseUrl, webDriverWait));
        pages.add(new EnterCodePage(webDriver, baseUrl, webDriverWait));
        pages.add(new ManualAddressPage(webDriver, baseUrl, webDriverWait));
        pages.add(new NamePage(webDriver, baseUrl, webDriverWait));
        pages.add(new NationalInsuranceNumberPage(webDriver, baseUrl, webDriverWait));
        pages.add(new PhoneNumberPage(webDriver, baseUrl, webDriverWait));
        pages.add(new ScotlandPage(webDriver, baseUrl, webDriverWait));
        pages.add(new InScotlandPage(webDriver, baseUrl, webDriverWait));
        pages.add(new SendCodePage(webDriver, baseUrl, webDriverWait));
        pages.add(new TermsAndConditionsPage(webDriver, baseUrl, webDriverWait));
        pages.add(new PostcodePage(webDriver, baseUrl, webDriverWait));
        pages.add(new SelectAddressPage(webDriver, baseUrl, webDriverWait));
        pages.add(new CookiesPage(webDriver, baseUrl, webDriverWait));
        pages.add(new ServerErrorPage(webDriver, baseUrl, webDriverWait));
        pages.add(new PrivacyNoticePage(webDriver, baseUrl, webDriverWait));
        pages.add(new PageNotFoundPage(webDriver, baseUrl, webDriverWait));
        //Guidance pages
        Arrays.stream(GuidancePageMetadata.values()).forEach(
                guidancePageMetadata -> pages.add(new GuidancePage(webDriver, baseUrl, webDriverWait, guidancePageMetadata.getPageName()))
        );
    }

    public BasePage getAndWaitForPageByName(PageName name) {
        BasePage page = getPageByName(name);
        page.waitForPageToLoad();
        return page;
    }

    public BasePage getPageByName(PageName name) {
        return pages.stream()
                .filter(basePage -> basePage.getPageName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not find page with name: " + name));
    }

    public CheckAnswersPage getCheckAnswersPage() {
        return (CheckAnswersPage) getAndWaitForPageByName(PageName.CHECK_ANSWERS);
    }

    public EnterCodePage getEnterCodePage() {
        return (EnterCodePage) getAndWaitForPageByName(PageName.ENTER_CODE);
    }

    public SendCodePage getSendCodePage() {
        return (SendCodePage) getAndWaitForPageByName(PageName.SEND_CODE);
    }

    public EmailAddressPage getEmailAddressPage() {
        return (EmailAddressPage) getAndWaitForPageByName(PageName.EMAIL_ADDRESS);
    }

    public PhoneNumberPage getPhoneNumberPage() {
        return (PhoneNumberPage) getAndWaitForPageByName(PageName.PHONE_NUMBER);
    }

    public ManualAddressPage getManualAddressPage() {
        return (ManualAddressPage) getAndWaitForPageByName(PageName.MANUAL_ADDRESS);
    }

    public NationalInsuranceNumberPage getNationalInsuranceNumberPage() {
        return (NationalInsuranceNumberPage) getAndWaitForPageByName(PageName.NATIONAL_INSURANCE_NUMBER);
    }

    public NamePage getNamePage() {
        return (NamePage) getAndWaitForPageByName(PageName.NAME);
    }

    public AreYouPregnantPage getAreYouPregnantPage() {
        return (AreYouPregnantPage) getAndWaitForPageByName(PageName.ARE_YOU_PREGNANT);
    }

    public ChildDateOfBirthPage getChildDateOfBirthPage() {
        return (ChildDateOfBirthPage) getAndWaitForPageByName(PageName.CHILD_DATE_OF_BIRTH);
    }

    public DoYouHaveChildrenPage getDoYouHaveChildrenPage() {
        return (DoYouHaveChildrenPage) getAndWaitForPageByName(PageName.DO_YOU_HAVE_CHILDREN);
    }

    public DateOfBirthPage getDateOfBirthPage() {
        return (DateOfBirthPage) getAndWaitForPageByName(PageName.DATE_OF_BIRTH);
    }

    public ScotlandPage getScotlandPage() {
        return (ScotlandPage) getAndWaitForPageByName(PageName.SCOTLAND);
    }

    public InScotlandPage getInScotlandPage() {
        return (InScotlandPage) getAndWaitForPageByName(PageName.IN_SCOTLAND);
    }

    public TermsAndConditionsPage getTermsAndConditionsPage() {
        return (TermsAndConditionsPage) getAndWaitForPageByName(PageName.TERMS_AND_CONDITIONS);
    }

    public TermsAndConditionsPage getTermsAndConditionsPageNoWait() {
        return (TermsAndConditionsPage) getPageByName(PageName.TERMS_AND_CONDITIONS);
    }

    public ConfirmationPage getConfirmationPage() {
        return (ConfirmationPage) getAndWaitForPageByName(PageName.CONFIRMATION);
    }

    public ConfirmationPage getConfirmationPageNoWait() {
        return (ConfirmationPage) getPageByName(PageName.CONFIRMATION);
    }

    public ConfirmUpdatedPage getConfirmUpdatedPage() {
        return (ConfirmUpdatedPage) getAndWaitForPageByName(PageName.CONFIRM_UPDATED);
    }

    public GuidancePage getGuidancePage(PageName pageName) {
        return (GuidancePage) getAndWaitForPageByName(pageName);
    }

    public GuidancePage getGuidancePageNoWait(PageName pageName) {
        return (GuidancePage) getPageByName(pageName);
    }

    public GenericPage getGenericPage() {
        return genericPage;
    }

    public PostcodePage getPostcodePage() {
        return (PostcodePage) getAndWaitForPageByName(PageName.POSTCODE);
    }

    public SelectAddressPage getSelectAddressPage() {
        return (SelectAddressPage) getAndWaitForPageByName(PageName.SELECT_ADDRESS);
    }

    public CookiesPage getCookiesPage() {
        return (CookiesPage) getAndWaitForPageByName(PageName.COOKIES);
    }

    public PageNotFoundPage getPageNotFoundPage() {
        return (PageNotFoundPage) getAndWaitForPageByName(PageName.PAGE_NOT_FOUND);
    }

    public PrivacyNoticePage getPrivacyNoticePage() {
        return (PrivacyNoticePage) getAndWaitForPageByName(PageName.PRIVACY_NOTICE);
    }
}
