package uk.gov.dhsc.htbhf.page.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Component for a radio button on a page
 */
public class RadioButton extends BaseComponent {

    public static final String YES = "yes";
    public static final String NO = "no";
    private String option;

    public RadioButton(WebDriver webDriver, String option) {
        super(webDriver);
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
}
