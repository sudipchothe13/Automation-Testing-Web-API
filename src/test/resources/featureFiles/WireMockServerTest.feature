@WireMockServerTest
Feature: WireMock API Testing

  Scenario: Verify employee creation using POST API
    Given I have stubbed the POST employee API
    When I send a POST request to "/employees"
    Then I verify the POST employee response

  Scenario: Verify employee details using GET API
    Given I have stubbed the GET employee API
    When I send a GET request to "/employees/3030"
    Then I verify the GET employee response
