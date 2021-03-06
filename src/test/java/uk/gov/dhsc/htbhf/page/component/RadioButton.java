package uk.gov.dhsc.htbhf.page.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Component for a radio button on a page
 */
public class RadioButton extends BaseComponent {

    public static final String YES = "yes";
    public static final String YES_LABEL = "Yes";
    public static final String NO = "no";
    public static final String NO_LABEL = "No";
    public static final String TEXT_LABEL = "Text";
    public static final String EMAIL_LABEL = "Email";
    public static final String TEXT = "text";
    public static final String EMAIL = "email";
    private String option;

    public RadioButton(WebDriver webDriver, WebDriverWait webDriverWait, String option) {
        super(webDriver, webDriverWait);
        this.option = option;
    }

    public void select() {
        WebElement radioButton = get();
        WebElement parentDiv = radioButton.findElement(By.xpath(".."));
        WebElement label = parentDiv.findElement(By.className("govuk-radios__label"));
        label.click();
    }

    public WebElement get() {
        String formattedCss = String.format("input[value=\"%s\"]", option);
        return findByCss(formattedCss);
    }

    public String getHint() {
        WebElement radioButton = get();
        WebElement div = radioButton.findElement(By.xpath(".."));
        WebElement hint = div.findElement(By.className("govuk-radios__hint"));
        return hint.getText();
    }
}
