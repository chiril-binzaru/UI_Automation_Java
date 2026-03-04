package org.example.testng;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pom.FormPom;
import org.example.utils.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;

public class FormTest {

    private static final Logger log = LogManager.getLogger(FormTest.class);

    static public WebDriver driver;
    static public String URL = "https://demoqa.com/";

    static public String FIRST_NAME = "Chiril";
    static public String LAST_NAME = "Binzaru";
    static public String EMAIL = "chirilbinzaru@gmail.com";
    static public String GENDER = "Male";
    static public String MOBILE = "5599900044";
    static public int BIRTH_DAY = 20;
    static public String BIRTH_MONTH = "May";
    static public String BIRTH_YEAR = "2005";
    static public String SUBJECT = "Biology";
    static public String HOBBY = "Sports";
    static public String ADDRESS = "Chisinau, 123 Street";
    static public String STATE = "NCR";
    static public String CITY = "Noida";

    static public String PICTURE_NAME = "test-picture.txt";

    @BeforeMethod
    public void beforeMethod() {
        log.info("Initializing WebDriver");
        driver = Driver.getDriverFromEnv();
        driver.manage().window().maximize();
        log.info("Browser launched and maximized");
    }

    @Test
    @Feature("Practice Form")
    @Description("Fill out the practice form and verify all submitted values")
    public void formTest() {
        log.info("Starting formTest");
        driver.get(URL);
        log.info("Navigated to {}", URL);
        FormPom formPom = new FormPom(driver);

        formPom.openPracticeForm();
        formPom.setFirstName(FIRST_NAME);
        formPom.setLastName(LAST_NAME);
        formPom.setEmail(EMAIL);
        formPom.setGender(GENDER);
        formPom.setMobile(MOBILE);
        formPom.setDateOfBirth(BIRTH_DAY, BIRTH_MONTH, BIRTH_YEAR);
        formPom.setSubject(SUBJECT);
        formPom.setHobby(HOBBY);

        String picturePath = Path.of("src", "test", "resources", "test-data", PICTURE_NAME)
                .toAbsolutePath()
                .toString();
        formPom.uploadPicture(picturePath);

        formPom.setCurrentAddress(ADDRESS);
        formPom.setStateAndCity(STATE, CITY);
        formPom.submit();

        log.info("Verifying submitted values");
        Assert.assertEquals(formPom.getSubmittedValue("Student Name"), FIRST_NAME + " " + LAST_NAME);
        Assert.assertEquals(formPom.getSubmittedValue("Student Email"), EMAIL);
        Assert.assertEquals(formPom.getSubmittedValue("Gender"), GENDER);
        Assert.assertEquals(formPom.getSubmittedValue("Mobile"), MOBILE);
        Assert.assertEquals(formPom.getSubmittedValue("Date of Birth"), "20 May,2005");
        Assert.assertEquals(formPom.getSubmittedValue("Subjects"), SUBJECT);
        Assert.assertEquals(formPom.getSubmittedValue("Hobbies"), HOBBY);
        Assert.assertEquals(formPom.getSubmittedValue("Picture"), PICTURE_NAME);
        Assert.assertEquals(formPom.getSubmittedValue("Address"), ADDRESS);
        Assert.assertEquals(formPom.getSubmittedValue("State and City"), STATE + " " + CITY);
        log.info("formTest passed");
        Thread.sleep(5000);
    }

    @AfterMethod
    public void afterMethod() {
        if (driver != null) {
            log.info("Closing browser");
            driver.quit();
        }
    }
}
