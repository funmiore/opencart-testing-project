package com.opencart.tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class OpenCartSteps {

    // 1. Declare the WebDriver globally

    WebDriver driver;

    // --- SETUP STEP (Used by all scenarios) ---
    @Given("I initialize the browser and navigate to the base URL")
    public void i_initialize_the_browser_and_navigate_to_the_base_url() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL);
        options.addArguments("--ignore-certificate-errors");
        options.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        System.out.println("Browser initialized.");
    }

    // --- REGISTRATION STEPS ---

    @Given("I am on the OpenCart registration page")
    public void i_am_on_the_open_cart_registration_page() {
        System.out.println("Navigating to registration page...");
        driver.get("http://opencart.abstracta.us/index.php?route=account/register");
        WebDriverWait urlWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        urlWait.until(ExpectedConditions.urlContains("account/register"));
    }

    @When("I fill in all required registration details")
    public void i_fill_in_all_required_registration_details() {
        System.out.println("Filling registration form details...");

        // Wait for the first input field to be visible before proceeding to fill the
        // form

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));

        // Generate a unique email every time
        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@example.com";

        driver.findElement(By.id("input-firstname")).sendKeys("Ela");
        driver.findElement(By.id("input-lastname")).sendKeys("Jones");
        driver.findElement(By.id("input-email")).sendKeys("jones@opencart.com");
        driver.findElement(By.id("input-telephone")).sendKeys("5551234567");
        driver.findElement(By.id("input-password")).sendKeys("SecureP@ss123");
        driver.findElement(By.id("input-confirm")).sendKeys("SecureP@ss123");
    }

    @When("I accept the privacy policy")
    public void i_accept_the_privacy_policy() {
        System.out.println("Accepting policy...");
        driver.findElement(By.name("agree")).click();
    }

    @When("I click the Continue button")
    public void i_click_the_continue_button() {
        System.out.println("Clicking Registration Continue...");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for a stable element (First Name input) to be present before finding the
        // button

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));

        driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div/input[2]")).click();
    }

    @Then("I should see the account creation success message")
    public void i_should_see_the_account_creation_success_message() {
        System.out.println("Verifying registration success...");
        String successXPath = "//*[@id=\"content\"]/h1";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            boolean successMessageDisplayed = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(successXPath))).isDisplayed();
            Assert.assertTrue(successMessageDisplayed, "Verification Failed: Registration failed.");
            System.out.println("TEST PASSED: Account successfully created.");

        } catch (org.openqa.selenium.TimeoutException e) {

            Assert.fail("Registration failed: Timeout waiting for success message.");

        } finally {

            // Close browser after successful registration test

            if (driver != null) {
                driver.quit();
            }
        }
    }
    // INVALID REGISTRATION WITH EXISTING CREDENTIALS

    @When("I fill in registration details with an existing email")
    public void i_fill_in_registration_details_with_an_existing_email() {
        System.out.println("Filling registration form details with existing email...");

        // Wait to ensure the form fields are ready

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));

        // Use an email that you know is already registered.

        String existingEmail = "richard@opencart.com"; // Using a known valid email!

        driver.findElement(By.id("input-firstname")).sendKeys("Richard");
        driver.findElement(By.id("input-lastname")).sendKeys("Mills");
        driver.findElement(By.id("input-email")).sendKeys("richard@opencart.com");
        driver.findElement(By.id("input-telephone")).sendKeys("5551234567");
        driver.findElement(By.id("input-password")).sendKeys("Opencart@1");
        driver.findElement(By.id("input-confirm")).sendKeys("Opencart@1");
    }

    @Then("I should see the warning message {string}")
    public void i_should_see_the_warning_message(String expectedMessage) {
        System.out.println("Verifying registration warning message...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By warningLocator = By.cssSelector("div.alert-danger");
        try {

            WebElement warningElement = wait.until(ExpectedConditions.visibilityOfElementLocated(warningLocator));

            String actualText = warningElement.getText().trim().toLowerCase();
            String lowerCaseExpected = expectedMessage.toLowerCase();

            // Assert that the actual text contains the expected message (robust check)

            Assert.assertTrue(actualText.contains(lowerCaseExpected),

                    "Verification Failed: Expected to find warning containing '" + expectedMessage +
                            "' but found: '" + warningElement.getText().trim() + "'"); // Display the ACTUAL text for
                                                                                       // debugging

            System.out.println("TEST PASSED: Correct warning message was displayed. ");

        } catch (org.openqa.selenium.TimeoutException e) {

            Assert.fail("Negative Test Failed: Timed out waiting for the warning message to appear.");

        } finally {

            // Close the browser at the end of this scenario

            if (driver != null) {
                driver.quit();
            }
        }
    }

    // INVALID REGISTRATION WITH MISSING EMAIL FIELD
    @When("I fill in registration details with a missing email")
    public void i_fill_in_registration_details_with_a_missing_email() {
        System.out.println("Filling registration form details, omitting email...");

        // Wait to ensure the form fields are ready

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));

        // Fill all fields *except* input-email

        driver.findElement(By.id("input-firstname")).sendKeys("Missing");
        driver.findElement(By.id("input-lastname")).sendKeys("Email");
        // driver.findElement(By.id("input-email")).sendKeys(""); // Intentionally
        // skipping this line
        driver.findElement(By.id("input-telephone")).sendKeys("5551234567");
        driver.findElement(By.id("input-password")).sendKeys("SecureP@ss123");
        driver.findElement(By.id("input-confirm")).sendKeys("SecureP@ss123");
    }

    @Then("I should see the email field validation message {string}")
    public void i_should_see_the_email_field_validation_message(String expectedMessage) {
        System.out.println("Verifying in-line email field validation message...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By emailErrorLocator = By.xpath("//*[contains(text(), '" + expectedMessage + "')]");

        try {

            // Wait for the error text element to be visible

            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailErrorLocator));

            Assert.assertTrue(errorElement.getText().contains(expectedMessage),

                    "Verification Failed: Element found but text did not match.");
            System.out.println("TEST PASSED: Correct in-line email validation message was displayed. ");

        } catch (org.openqa.selenium.TimeoutException e) {

            Assert.fail(
                    "Negative Test Failed: Timed out waiting for the validation message to appear: " + expectedMessage);

        } finally {

            // Close the browser at the end of this scenario

            if (driver != null) {

                driver.quit();
            }
        }
    }

        @When("I fill in registration details with mismatched passwords")
public void i_fill_in_registration_details_with_mismatched_passwords() {
    System.out.println("Filling registration form details with mismatched passwords...");
    
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));

    String uniqueEmail = "mismatch" + System.currentTimeMillis() + "@example.com";
    
    driver.findElement(By.id("input-firstname")).sendKeys("Bad");
    driver.findElement(By.id("input-lastname")).sendKeys("Pass");
    driver.findElement(By.id("input-email")).sendKeys(uniqueEmail); 
    driver.findElement(By.id("input-telephone")).sendKeys("5551234567");
    // Enter two different passwords
    driver.findElement(By.id("input-password")).sendKeys("PasswordA1");
    driver.findElement(By.id("input-confirm")).sendKeys("PasswordB2");
}

@Then("I should see the password field validation message {string}")
public void i_should_see_the_password_field_validation_message(String expectedMessage) {
    System.out.println("Verifying password validation message...");
    
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    // Locator needs to target the error message on the page
    By passwordErrorLocator = By.xpath("//*[contains(text(), '" + expectedMessage + "')]");

    try {
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordErrorLocator));
        Assert.assertTrue(errorElement.getText().contains(expectedMessage), 
            "Verification Failed: Password error text did not match.");
        System.out.println("TEST PASSED: Correct password validation message was displayed.");
    } catch (org.openqa.selenium.TimeoutException e) {
        Assert.fail("Negative Test Failed: Timed out waiting for the password validation message to appear: " + expectedMessage);
    } finally {
        if (driver != null) {
            driver.quit(); 
        }
    }
}
    
    // --- LOGIN STEPS ---
    @Given("I am on the OpenCart login page")
    public void i_am_on_the_open_cart_login_page() {
        System.out.println("Navigating to login page...");
        driver.get("http://opencart.abstracta.us/index.php?route=account/login");
    }

    @When("I enter a valid email and password")
    public void i_enter_a_valid_email_and_password() {
        System.out.println("Entering login credentials...");

        // Use a working credential here!

        driver.findElement(By.id("input-email")).sendKeys("lasttest@example.com");
        driver.findElement(By.id("input-password")).sendKeys("TestPass123");
    }

    @When("I click the Login button")
    public void i_click_the_login_button() {
        System.out.println("Clicking Login button...");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By loginButtonLocator = By.cssSelector("input[value='Login']");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
        loginButton.click();

        // Check for an immediate failure message (Red Alert Box) after click
    }

    @Then("I should be redirected to the My Account page")
    public void i_should_be_redirected_to_the_my_account_page() {
        System.out.println("Verifying successful login...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By myAccountHeaderLocator = By.xpath("//*[@id=\"content\"]/h2[1]");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(myAccountHeaderLocator));
            System.out.println("TEST PASSED: User successfully logged in.");
        } catch (org.openqa.selenium.TimeoutException e) {

            Assert.fail("Verification Failed: Timed out waiting for 'My Account' page. Login was likely unsuccessful.");
        }

        // NO driver.quit() here! We need the browser session for the Logout test.

    }
    // TEST FOR INCORRECT PASSWORD

    @When("I enter a valid email and an incorrect password")

    public void i_enter_a_valid_email_and_an_incorrect_password() {

        System.out.println("Entering login credentials with incorrect password...");

        // Wait to ensure the form fields are ready
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-email")));

        // Use the same valid email as the success scenario!
        driver.findElement(By.id("input-email")).sendKeys("richard@opencart.com");
        // Enter an incorrect password
        driver.findElement(By.id("input-password")).sendKeys("WrongPassword123");
    }

    @When("I enter an unregistered email and password")
public void i_enter_an_unregistered_email_and_password() {
    System.out.println("Attempting login with unregistered email...");

    // Generate a unique, random email that will definitely not be in the system
    String unregisteredEmail = "fakeuser" + System.currentTimeMillis() + "@testdomain.com"; 

    // Use an explicit wait to ensure the form fields are ready
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-email")));

    driver.findElement(By.id("input-email")).sendKeys(unregisteredEmail); 
    driver.findElement(By.id("input-password")).sendKeys("TestP@ss123"); 
}

    // ... LOGOUT STEPS

    @When("I click the My Account link to open the dropdown")
    public void i_click_the_my_account_link_to_open_the_dropdown() {
        System.out.println("Clicking My Account link to open dropdown...");
        // Find the 'My Account' text link on the top right and click it
        WebElement myAccountLink = driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[1]"));
        myAccountLink.click();
    }

    @When("I click the Logout link in the dropdown")
    public void i_click_the_logout_link_in_the_dropdown() {
        System.out.println("Clicking Logout link from dropdown...");

        // Wait to ensure the dropdown menu has opened completely
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Locate and click the Logout link, which is now clickable

        WebElement logoutLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[5]/a"))

        );

        logoutLink.click();
    }

    @Then("I should be redirected to the Account Logout success page")
    public void i_should_be_redirected_to_the_account_logout_success_page() {
        System.out.println("Verifying logout success...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for the 'Account Logout' heading on the success page
        By logoutHeaderLocator = By.xpath("//*[@id=\"content\"]/h1");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(logoutHeaderLocator));
            System.out.println("TEST PASSED: User successfully logged out. ");
        } catch (org.openqa.selenium.TimeoutException e) {
            Assert.fail("Verification Failed: Timed out waiting for Account Logout success page.");
        } finally {

            if (driver != null) {
                driver.quit();
            }
        }
    }
}