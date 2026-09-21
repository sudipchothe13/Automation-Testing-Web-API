Feature: My details API testing with POJO

@MyDetails
  Scenario: Test My details API
    Given user complete pre-condition
    When user hit Post request
    Then user verify MyDetails API details
