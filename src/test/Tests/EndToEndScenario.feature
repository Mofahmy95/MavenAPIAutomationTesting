Feature: E2E Scenario

  Scenario: Create - Login - Update - Get
    Given Create New User
    Then  Successful SignIn
    Then  Update User
    Then User Gets All Signed up Users