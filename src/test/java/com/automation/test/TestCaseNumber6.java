package com.automation.test;

import com.automation.utilities.BrowserUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestCaseNumber6 {
    private WebDriver driver;
    private String URLTempEmail = "https://www.tempmailaddress.com";
    private String URLCyberTek = "https://practice-cybertekschool.herokuapp.com";

    private By fullNameBy = By.xpath("//input[@type='text']");
    private By emailBy = By.xpath("//input[@type='email']");

    private By signUpBy = By.xpath("//button[@type='submit']");

    private By signUpMessageBy = By.xpath("//h3[@name='signup_message']");
    private By senderEmailBy = By.xpath("//*[@class='from']");
    //    private By senderEmailBy = By.xpath("(//tbody/tr/td/span[@class='glyphicon glyphicon-envelope'])[1]");
    private By senderFromBy = By.id("odesilatel");
    private By subjectBy = By.id("predmet");

    private By fileUploadBy = By.linkText("File Upload");
    private By fileUploadButtonBy = By.id("file-upload");

    private By uploadButtonBy = By.id("file-submit");
    private By messageBy = By.xpath("//h3");

    private By autoCompleteBy = By.linkText("Autocomplete");

    private By countryTextBoxBy = By.id("myCountry");
    private By submitButtonBy = By.xpath("//input[@type='button']");
    private By resultMessage = By.id("result");

    private By statusCodesBy = By.linkText("Status Codes");

    private By statusCodeMessageBy = By.xpath("//p");


    @Test (description = "TestCase6")
    public void signUpForMailingListTest() {
        driver.get(URLTempEmail);
        driver.manage().window().maximize();
        BrowserUtils.wait(2);
        String email = driver.findElement(By.id("email")).getText();
        BrowserUtils.wait(3);
        driver.navigate().to(URLCyberTek);
        BrowserUtils.wait(3);
        driver.findElement(By.linkText("Sign Up For Mailing List")).click();
        BrowserUtils.wait(1);
        driver.findElement(fullNameBy).sendKeys("John Smith");
        BrowserUtils.wait(1);
        driver.findElement(emailBy).sendKeys(email);
        BrowserUtils.wait(1);
        driver.findElement(signUpBy).click();
        BrowserUtils.wait(3);
        String expected = "Thank you for signing up. Click the button below to return to the home page.";
        String actual = driver.findElement(signUpMessageBy).getText();
        BrowserUtils.wait(1);
        Assert.assertEquals(actual, expected);

        driver.navigate().to(URLTempEmail);
        BrowserUtils.wait(1);

        String expectedEmail = "do-not-reply@practice.cybertekschool.com";
        String actualEmail = driver.findElement(senderEmailBy).getText().trim();

        Assert.assertEquals(actualEmail, expectedEmail);

        driver.findElement(senderEmailBy).click();

        String expectedEmailFrom = "do-not-reply@practice.cybertekschool.com";
        String actualEmailFrom = driver.findElement(senderFromBy).getText();

        Assert.assertEquals(actualEmailFrom, expectedEmailFrom);

        String expectedSubject = "Thanks for subscribing to practice.cybertekschool.com!";
        String actualSubject = driver.findElement(subjectBy).getText();

        Assert.assertEquals(actualSubject, expectedSubject);
    }

    @Test (description = "TestCase7")
    public void fileUploadTest() {
        driver.get(URLCyberTek);
        BrowserUtils.wait(2);
        driver.findElement(fileUploadBy).click();
        BrowserUtils.wait(2);
        driver.findElement(fileUploadButtonBy).sendKeys("C:\\Users\\Admin\\IdeaProjects\\vyTrack\\src\\test\\java\\com\\automation\\test\\TestCase7.txt");
//        driver.findElement(fileUploadButtonBy).sendKeys("TestCase7.txt");
        BrowserUtils.wait(2);
        driver.findElement(uploadButtonBy).click();
        BrowserUtils.wait(2);
        String expected = "File Uploaded!";
        String actual = driver.findElement(messageBy).getText();
        BrowserUtils.wait(2);
        Assert.assertEquals(actual, expected);
    }

    @Test (description = "TestCase8")
    public void autoCompeteTest() {
        driver.get(URLCyberTek);
        BrowserUtils.wait(2);
        driver.findElement(autoCompleteBy).click();
        BrowserUtils.wait(2);
        driver.findElement(countryTextBoxBy).sendKeys("United States of America");
        BrowserUtils.wait(1);
        driver.findElement(submitButtonBy).click();

        String expected = "You selected: United States of America";
        String actual = driver.findElement(resultMessage).getText();

        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "testData")
    public static Object[] testData() {
        return new Object[]{"200", "301", "404", "500"};
    }


    @Test(dataProvider = "testData", description = "TestCase9thru12")
    public void codeStatusTest(String code) {
        driver.get("https://practice-cybertekschool.herokuapp.com/");
        BrowserUtils.wait(2);
        driver.findElement(statusCodesBy).click();
        BrowserUtils.wait(2);
        driver.findElement(By.linkText(code)).click();

        String expected = "This page returned a " + code + " status code.";
        String actual = driver.findElement(statusCodeMessageBy).getText();

        Assert.assertTrue(actual.contains(expected), "The status code does not exist");

    }


    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().version("79").setup();
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void teardown() {
        // if webDriver object is alive
        if (driver != null) {
            // close the browser, close session
            driver.quit();
            // destroy webDriver object for sure
            driver = null;
        }
    }


}
