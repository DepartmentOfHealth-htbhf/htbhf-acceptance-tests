package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page object for the page where the customer can check their answers before submitting.
 */
public class CheckAnswersPage extends SubmittablePage {

    private static final String GOV_LIST_HEADER_CLASSNAME = "govuk-summary-list__key";
    private static final String GOV_LIST_VALUE_CLASSNAME = "govuk-summary-list__value";
    private static final String GOV_LIST_ROW_CLASSNAME = ".govuk-summary-list__row";
    private static final String GOV_LIST_ACTION_CLASSNAME = "govuk-summary-list__actions";
    private static final String GOV_LINK_CLASSNAME = "govuk-link";
    private static final String GOV_HIDDEN_CLASSNAME = "govuk-visually-hidden";
    private static final String CLAIM_SUMMARY_PARENT_ID = "#claim-summary";
    private static final String CHILDREN_SUMMARY_PARENT_ID = "#children-summary";

    public CheckAnswersPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/check-answers";
    }

    @Override
    String getPageName() {
        return "check answers";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Check your answers";
    }

    public List<CheckDetailsRowData> getClaimSummaryListContents() {
        return getContentsOfSummaryListsByParentId(CLAIM_SUMMARY_PARENT_ID);
    }

    public List<CheckDetailsRowData> getChildrenSummaryListContents() {
        return getContentsOfSummaryListsByParentId(CHILDREN_SUMMARY_PARENT_ID);
    }

    private List<CheckDetailsRowData> getContentsOfSummaryListsByParentId(String parentId) {
        List<WebElement> tableRows = findAllByCss(parentId + " " + GOV_LIST_ROW_CLASSNAME);
        return tableRows.stream().map(this::getDataForRow).collect(Collectors.toList());
    }

    private CheckDetailsRowData getDataForRow(WebElement tableRow) {
        WebElement header = tableRow.findElement(By.className(GOV_LIST_HEADER_CLASSNAME));
        String headerText = header.getText();
        WebElement value = tableRow.findElement(By.className(GOV_LIST_VALUE_CLASSNAME));
        String valueText = value.getText();
        CheckDetailsAction action = getActionForRow(tableRow);
        return CheckDetailsRowData.builder()
                .header(headerText)
                .value(valueText)
                .action(action)
                .build();
    }

    private CheckDetailsAction getActionForRow(WebElement row) {
        List<WebElement> actions = row.findElements(By.className(GOV_LIST_ACTION_CLASSNAME));
        return CollectionUtils.isEmpty(actions) ? null : this.getActionFromElement(actions.get(0));
    }

    private CheckDetailsAction getActionFromElement(WebElement actionElement) {
        WebElement changeLink = actionElement.findElement(By.className(GOV_LINK_CLASSNAME));
        String changeUrl = changeLink.getAttribute("href");
        String changeText = changeLink.getText();
        WebElement hiddenSpan = changeLink.findElement(By.className(GOV_HIDDEN_CLASSNAME));
        String hiddenText = hiddenSpan.getText();
        return CheckDetailsAction.builder()
                .url(changeUrl)
                .text(changeText.replace(hiddenText, "").trim())
                .hiddenText(hiddenText)
                .build();
    }

}
