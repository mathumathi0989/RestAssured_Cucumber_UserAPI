#Author: Mathumathi

 Feature: Update User API


Scenario: Check if Admin is able to update a user with all valid credentials
    Given Admin set the PUT request with the valid request body and valid Endpoint
    When Admin sends HTTPS Request with request Body
    Then Admin receives "200" "OK" Status for update user
