#Author: Mathumathi


Feature: GetAllUsers

Background: Admin sets the Valid Basic Auth for Authorization

Scenario: Check if Admin is able to Get All User List with valid credentials
Given Admin set the GET request with valid Endpoint
When Admin sends HTTPS Request with endpoint
Then Admin receives "200" "OK" Status Code and should display all the users in response body.


