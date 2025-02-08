#Author: Mathumathi

 Feature: Delete User By UserID API

Scenario: Check if Admin is able to DELETE user by valid USER ID
    Given Admin set the DELETE request by user id
    When Admin sends the DELETE HTTP request by User ID
    Then Admin receives the "200" "OK" Status code with message

Scenario: Check if Admin is able to DELETE user by already deleted USER ID
		Given Admin set the DELETE request by user id
		When  Admin sends DELETE HTTPS Request with already deleted userID
		Then Admin receives the "404" "Not Found" Status
		
Scenario: Check if Admin is able to DELETE user by invalid USER ID
		Given Admin set the DELETE request by user id
		When  Admin sends DELETE HTTPS Request with invalid userID
		Then Admin receives the "404" "Not Found" Status
		
Scenario: Check if Admin is able to DELETE user by user id with invalid request type
		Given Admin set the DELETE request by user id
		When  Admin sends HTTPS Request with invalid requestType by userID
		Then Admin receives the "405" "Method Not Allowed" Status
	
Scenario: Check if Admin is able to DELETE user by USER ID with invaid Basic Auth
		Given Admin set the DELETE request by user id with invalid basic auth
		When  Admin sends the DELETE HTTP request by User ID
		Then Admin receives the "401" "Unauthorized" Status
		
Scenario: Check if Admin is able to DELETE user by USER ID with No Auth
		Given Admin set the DELETE request by user id with No Auth
		When  Admin sends the DELETE HTTP request by User ID
		Then Admin receives the "401" "Unauthorized" Status