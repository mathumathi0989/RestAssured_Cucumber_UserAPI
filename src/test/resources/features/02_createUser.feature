#Author: Mathumathi

 Feature: Create User API

Scenario: Check if Admin is able to create a user with all valid credentials
    Given Admin set the POST request with the valid request body and valid Endpoint
    When Admin sends HTTPS Request and request Body with endpoint
    Then Admin receives "201" "Created" Status
