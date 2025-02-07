#Author: Mathumathi


Feature: GetAllUsers

Scenario: Check if Admin is able to Get All User List with valid credentials
Given Admin set the GET request
When Admin sends HTTPS Request with endpoint
Then Admin receives "200" "OK" Status Code

Scenario: Check if Admin is able to Get All User List without endpoint
Given Admin set the GET request
When Admin sends HTTPS Request without endpoint
Then Admin receives "404" "Not Found" Status Code 

Scenario: Check if Admin is unable to Get All User List with invalid endpoint
Given Admin set the GET request
When Admin sends HTTPS Request with invalid endpoint
Then Admin receives "404" "Not Found" Status Code 

Scenario: Check if Admin is able to Get  all user with No Auth
Given Admin set the GET request without Auth
When Admin sends HTTPS Request with endpoint
Then Admin receives "401" "Unauthorized" Status Code 

Scenario: Check if Admin is able to Get  all user with Invalid Basic Auth
Given Admin set the GET request with Invalid Basic Auth
When Admin sends HTTPS Request with endpoint
Then Admin receives "401" "Unauthorized" Status Code 