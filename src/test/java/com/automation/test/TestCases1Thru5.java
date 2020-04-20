package com.automation.test;

import com.automation.utilities.BrowserUtils;
import com.automation.utilities.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestCases1Thru5 {
    private WebDriver driver;
    private String URL = "https://practice-cybertekschool.herokuapp.com";
    private By registration_formBy = By.linkText("Registration Form");

    private By firstNameBy = By.name("firstname"); // private By firstNameBy = By.xpath("//input[@name='firstname']"); // Xpath practice
    private By lastNameBy = By.name("lastname"); //     private By lastNameBy = By.xpath("//input[@name='lastname']"); // Xpath practice
    private By usernameBy = By.name("username");
    private By emailBy = By.name("email");
    private By passwordBy = By.name("password");
    private By phoneBy = By.name("phone");
    private By genderMaleBy = By.xpath("//input[@value='male']");
    private By birthdayBy = By.name("birthday");
    private By departmentBy = By.xpath("//select[@name='department']");
    private By job_titleBy = By.xpath("//select[@name='job_title']");

    private By cPlusPlusBy = By.xpath("//label[text()='C++']/preceding-sibling::input");
    private By javaBy = By.xpath("//label[text()='Java']/preceding-sibling::input");
    private By javaScriptBy = By.xpath("//label[text()='JavaScript']/preceding-sibling::input");

    private By signUpBtnBy = By.xpath("//button[@id='wooden_spoon']");

    private By firstNameLengthBy = By.xpath("(//small[@data-bv-validator='stringLength'])[1]");
    private By lastNameLengthBy = By.xpath("(//small[@data-bv-validator='stringLength'])[2]");

    @Test(description = "Test Case #1")
    public void registrationFormBirthDayTest() {
        driver.findElement(birthdayBy).sendKeys("wrong_dob");
        BrowserUtils.wait(1);
        String expected = "The date of birth is not valid";
        String actual = driver.findElement(By.xpath("//small[text()='The date of birth is not valid']")).getText();
        BrowserUtils.wait(2);
        assertEquals(actual, expected);
    }

    @Test(description = "Test Case #2")
    public void registrationFormLanguageTest() {
        boolean isCPlusPlusDisplayed = driver.findElement(cPlusPlusBy).isDisplayed();
        boolean isJavaDisplayed = driver.findElement(javaBy).isDisplayed();
        boolean isJavaScriptDisplayed = driver.findElement(javaScriptBy).isDisplayed();
        assertTrue(isCPlusPlusDisplayed && isJavaDisplayed && isJavaScriptDisplayed);
    }

    @Test(description = "Test Case #3")
    public void registrationFormFirstNameTest() {
        // first name must be more than 2 and less than 64 characters long
        driver.findElement(firstNameBy).sendKeys("a");
        BrowserUtils.wait(2);
        String expected = "first name must be more than 2 and less than 64 characters long";
        String actual = driver.findElement(firstNameLengthBy).getText();
        BrowserUtils.wait(2);
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "Test Case #4")
    public void registrationFormLastNameTest() {
        // The last name must be more than 2 and less than 64 characters long
        driver.findElement(lastNameBy).sendKeys("a");
        BrowserUtils.wait(2);
        String expected = "The last name must be more than 2 and less than 64 characters long";
        String actual = driver.findElement(lastNameLengthBy).getText();
        BrowserUtils.wait(2);
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "Test Case #5")
    public void registrationFormCompleteTest() {
        Student s1 = new Student("Ali", "As", "ljlkjlkjlk", "alksjdlkasdj@dada.com", "pa$$w0rd", "713-713-7137", "01/01/1985");
        driver.findElement(firstNameBy).sendKeys(s1.firstName);
        driver.findElement(lastNameBy).sendKeys(s1.lastName);
        driver.findElement(usernameBy).sendKeys(s1.username);
        driver.findElement(emailBy).sendKeys(s1.email);
        driver.findElement(passwordBy).sendKeys(s1.password);
        driver.findElement(phoneBy).sendKeys(s1.phone);
        driver.findElement(genderMaleBy).click();
        driver.findElement(birthdayBy).sendKeys(s1.dateOfBirth);
        WebElement departmentDropdown = driver.findElement(departmentBy);
        Select departmentSelect = new Select(departmentDropdown);
        departmentSelect.selectByValue("DA");
        WebElement job_titleDropdown = driver.findElement(job_titleBy);
        Select jobTitleSelect = new Select(job_titleDropdown);
        jobTitleSelect.selectByVisibleText("SDET");
        driver.findElement(javaBy).click();
        driver.findElement(signUpBtnBy).click();
        BrowserUtils.wait(2);
        String expected = "You've successfully completed registration!";
        String actual = driver.findElement(By.xpath("//p")).getText();
        BrowserUtils.wait(2);
        Assert.assertEquals(actual, expected);
    }

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.createADriver("chrome");
        driver.get(URL);
        driver.manage().window().maximize();
        BrowserUtils.wait(2);
        driver.findElement(registration_formBy).click();
        BrowserUtils.wait(2);
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
