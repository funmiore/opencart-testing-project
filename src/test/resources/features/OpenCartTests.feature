Feature: OpenCart Core Functionality Tests

  # --- Part 1: Registration Tests (4 Scenarios) ---
  # As a potential shopper
  # I want to register an account
  # So that I can log in and place orders

  Scenario: 1. Successful new user registration
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart registration page
    When I fill in all required registration details
    And I accept the privacy policy
    And I click the Continue button
    Then I should see the account creation success message

  Scenario: 2. Invalid user registration with existing account
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart registration page
    When I fill in registration details with an existing email
    And I accept the privacy policy
    And I click the Continue button
    Then I should see the warning message "Warning: E-Mail Address is already registered!"

  Scenario: 3. Invalid user registration without accepting privacy policy
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart registration page
    When I fill in all required registration details
    # We intentionally skip the step: And I accept the privacy policy
    And I click the Continue button
    Then I should see the warning message "Warning: You must agree to the Privacy Policy!"

  Scenario: 4. Invalid user registration with missing mandatory email
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart registration page
    When I fill in registration details with a missing email
    And I accept the privacy policy
    And I click the Continue button
    Then I should see the email field validation message "E-Mail Address does not appear to be valid!"


  # --- Part 2 & 3: Login and Logout Tests (5 Scenarios) ---
  # As a registered OpenCart customer
  # I want to log in and log out
  # So that I can access my account securely

  Scenario: 5. Successful user login and logout
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart login page
    When I enter a valid email and password
    And I click the Login button
    Then I should be redirected to the My Account page
    
    # LOGOUT STEPS
    When I click the My Account link to open the dropdown
    And I click the Logout link in the dropdown
    Then I should be redirected to the Account Logout success page

  Scenario: 6. Invalid user login with incorrect password
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart login page
    When I enter a valid email and an incorrect password
    And I click the Login button
    Then I should see the warning message "Warning: No match for E-Mail Address and/or Password."

  Scenario: 7. Invalid user login with unregistered email
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart login page
    When I enter an unregistered email and password
    And I click the Login button
    Then I should see the warning message "Warning: No match for E-Mail Address and/or Password."

  # --- New Scenarios Added to Reach 9 ---

  Scenario: 8. Invalid user login with empty credentials
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart login page
    When I click the Login button
    Then I should see the warning message "Warning: No match for E-Mail Address and/or Password."

  Scenario: 9. Invalid user registration with mismatched passwords
    Given I initialize the browser and navigate to the base URL
    And I am on the OpenCart registration page
    When I fill in registration details with mismatched passwords
    And I accept the privacy policy
    And I click the Continue button
    Then I should see the password field validation message "Password confirmation does not match password!"