#Author: Mathumathi

 Feature: Get User By UserID API

Scenario: Check if Admin is able to GET user by valid USER ID
    Given Admin set the GET request by user id
    When Admin sends the GET HTTP request by User ID
    Then Admin receives the "200" "OK" Status code with valid user details

Scenario: Check if Admin is able to GET user by invalid USER ID
		Given Admin set the GET request by user id
		When  Admin sends HTTPS Request with invalid userID
		Then Admin receives the "404" "Not Found" Status code with valid user details
		
Scenario: Check if Admin is able to GET user by user id with invalid request type
		Given Admin set the GET request by user id
		When  Admin sends HTTPS Request with invalid requestType
		Then Admin receives the "405" "Method Not Allowed" Status code with valid user details
	
Scenario: Check if Admin is able to GET user by USER ID with invaid Basic Auth
		Given Admin set the GET request by user id with invalid basic auth
		When  Admin sends the GET HTTP request by User ID
		Then Admin receives the "401" "Unauthorized" Status code with valid user details
		
Scenario: Check if Admin is able to GET user by USER ID with No Auth
		Given Admin set the GET request by user id with No Auth
		When  Admin sends the GET HTTP request by User ID
		Then Admin receives the "401" "Unauthorized" Status code with valid user details