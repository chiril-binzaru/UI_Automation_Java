package org.example.pom;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class FormPom {

    private static final Logger log = LogManager.getLogger(FormPom.class);

    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    private final By formsCard = By.xpath("//*[text()='Forms']");
    private final By practiceFormMenu = By.xpath("//*[text()='Practice Form']");

    private final By firstName = By.id("firstName");
    private final By lastName = By.id("lastName");
    private final By userEmail = By.id("userEmail");
    private final By userNumber = By.id("userNumber");
    private final By dateOfBirthInput = By.id("dateOfBirthInput");
    private final By subjectsInput = By.id("subjectsInput");
    private final By uploadPicture = By.id("uploadPicture");
    private final By currentAddress = By.id("currentAddress");
    private final By stateContainer = By.id("state");
    private final By cityContainer = By.id("city");
    private final By submitButton = By.id("submit");
    private final By modalTitle = By.id("example-modal-sizes-title-lg");

    public FormPom(WebDriver driverParam) {
        this.driver = driverParam;
        this.js = (JavascriptExecutor) driverParam;
        this.wait = new WebDriverWait(driverParam, Duration.ofSeconds(15));
    }

    @Step("Open Practice Form")
    public void openPracticeForm() {
        log.info("Navigating to Practice Form");
        screenshot("before");
        closeAdvert();
        jsClick(formsCard);
        closeAdvert();
        jsClick(practiceFormMenu);
        closeAdvert();
        log.info("Practice Form opened");
        screenshot("after");
    }

    @Step("Set first name: {value}")
    public void setFirstName(String value) {
        log.debug("Setting first name: {}", value);
        screenshot("before");
        type(firstName, value);
        screenshot("after");
    }

    @Step("Set last name: {value}")
    public void setLastName(String value) {
        log.debug("Setting last name: {}", value);
        screenshot("before");
        type(lastName, value);
        screenshot("after");
    }

    @Step("Set email: {value}")
    public void setEmail(String value) {
        log.debug("Setting email: {}", value);
        screenshot("before");
        type(userEmail, value);
        screenshot("after");
    }

    @Step("Select gender: {gender}")
    public void setGender(String gender) {
        log.debug("Selecting gender: {}", gender);
        screenshot("before");
        By genderLocator = By.xpath("//*[@id='genterWrapper']//label[text()='" + gender + "']");
        wait.until(ExpectedConditions.elementToBeClickable(genderLocator)).click();
        screenshot("after");
    }

    @Step("Set mobile: {value}")
    public void setMobile(String value) {
        log.debug("Setting mobile: {}", value);
        screenshot("before");
        type(userNumber, value);
        screenshot("after");
    }

    @Step("Set date of birth: {day} {month} {year}")
    public void setDateOfBirth(int day, String month, String year) {
        log.debug("Setting date of birth: {} {} {}", day, month, year);
        screenshot("before");
        wait.until(ExpectedConditions.elementToBeClickable(dateOfBirthInput)).click();
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker__month-select"))))
                .selectByVisibleText(month);
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker__year-select"))))
                .selectByVisibleText(year);
        By dayLocator = By.xpath("//div[contains(@class,'react-datepicker__day') and not(contains(@class,'react-datepicker__day--outside-month')) and text()='" + day + "']");
        wait.until(ExpectedConditions.elementToBeClickable(dayLocator)).click();
        screenshot("after");
    }

    @Step("Set subject: {subject}")
    public void setSubject(String subject) {
        log.debug("Setting subject: {}", subject);
        screenshot("before");
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(subjectsInput));
        input.sendKeys(subject);
        input.sendKeys(Keys.ENTER);
        screenshot("after");
    }

    @Step("Select hobby: {hobby}")
    public void setHobby(String hobby) {
        log.debug("Selecting hobby: {}", hobby);
        screenshot("before");
        By hobbyLocator = By.xpath("//*[@id='hobbiesWrapper']//label[text()='" + hobby + "']");
        wait.until(ExpectedConditions.elementToBeClickable(hobbyLocator)).click();
        screenshot("after");
    }

    @Step("Upload picture: {absolutePath}")
    public void uploadPicture(String absolutePath) {
        log.debug("Uploading picture: {}", absolutePath);
        screenshot("before");
        wait.until(ExpectedConditions.presenceOfElementLocated(uploadPicture)).sendKeys(absolutePath);
        screenshot("after");
    }

    @Step("Set current address: {value}")
    public void setCurrentAddress(String value) {
        log.debug("Setting current address: {}", value);
        screenshot("before");
        type(currentAddress, value);
        screenshot("after");
    }

    @Step("Set state: {state}, city: {city}")
    public void setStateAndCity(String state, String city) {
        log.debug("Setting state: {}, city: {}", state, city);
        screenshot("before");
        closeAdvert();
        jsClick(stateContainer);
        WebElement stateInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("react-select-3-input")));
        stateInput.sendKeys(state);
        stateInput.sendKeys(Keys.ENTER);
        closeAdvert();
        jsClick(cityContainer);
        WebElement cityInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("react-select-4-input")));
        cityInput.sendKeys(city);
        cityInput.sendKeys(Keys.ENTER);
        screenshot("after");
    }

    @Step("Submit the form")
    public void submit() {
        log.info("Submitting the form");
        screenshot("before");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        button.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitle));
        log.info("Form submitted successfully");
        screenshot("after");
    }

    public String getSubmittedValue(String label) {
        By valueLocator = By.xpath("//td[text()='" + label + "']/following-sibling::td");
        String value = wait.until(ExpectedConditions.visibilityOfElementLocated(valueLocator)).getText().trim();
        log.debug("Submitted value for '{}': {}", label, value);
        return value;
    }

    public void closeAdvert() {
        log.debug("Removing ad overlays");
        try {
            js.executeScript("var elem = document.evaluate(\"//*[@id='adplus-anchor']\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                    "if (elem && elem.parentNode) { elem.parentNode.removeChild(elem); }");
        } catch (Exception ignored) {
        }
        try {
            js.executeScript("var elem = document.evaluate(\"//footer\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;" +
                    "if (elem && elem.parentNode) { elem.parentNode.removeChild(elem); }");
        } catch (Exception ignored) {
        }
    }

    private void screenshot(String name) {
        byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(bytes), "png");
    }

    private void jsClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        js.executeScript("arguments[0].click();", element);
    }

    private void type(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(value);
    }
}
