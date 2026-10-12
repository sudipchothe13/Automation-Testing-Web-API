Feature: CRUD operations

  @personalDetails
  Scenario: Validate Personal Details API POST call
    Given initialize request for Personal Details POST API
    When user sends Personal Details POST request
    Then verify Personal Details API response with assertions
