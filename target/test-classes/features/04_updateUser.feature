#Author: Mathumathi

 Feature: Update User API


Scenario: Check if Admin is able to update a user with all valid credentials
    Given Admin set the PUT request with the valid request body and valid Endpoint
    When Admin sends HTTPS Request with request Body
    Then Admin receives "200" "OK" Status for update user

Scenario: Check if Admin is able to update a user with No Auth
    Given Admin set the PUT request with the valid request body with no Auth
    When Admin sends HTTPS Request and update request Body with no or invalid auth
    Then Admin receives "401" "Unauthorized" Status for update

Scenario: Check if Admin is able to update a user with invalid basic auth
    Given Admin set the PUT request with the valid request body with invalid basic Auth
    When Admin sends HTTPS Request and update request Body with no or invalid auth
    Then Admin receives "401" "Unauthorized" Status for update	