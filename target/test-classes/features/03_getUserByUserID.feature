#Author: Mathumathi

 Feature: Get User By UserID API

Scenario: Check if Admin is able to GET user by valid USER ID
    Given Admin set the GET request by user id
    When Admin sends the GET HTTP request by User ID
    Then Admin receives the "200" "OK" Status code with valid user details

