#Author: Mathumathi

Feature: Get User By UserFirstName API

@sanity
Scenario: Check if Admin is able to GET user by valid USER FirstName
    Given Admin set the GET request by user firstName
    When Admin sends the GET HTTP request by User FirstName
    Then Admin receives the "200" "OK" Status code for UserFirstName

Scenario: Check if Admin is able to GET user by invalid USER FirstName
		Given Admin set the GET request by user firstName
		When  Admin sends HTTPS Request with invalid userfirstName
		Then Admin receives the "404" "Not Found" Status code for UserFirstName
		
Scenario: Check if Admin is able to GET user by user FirstName with invalid request type
		Given Admin set the GET request by user firstName
		When  Admin sends HTTPS Request with invalid requestType by User firstName
		Then Admin receives the "405" "Method Not Allowed" Status code for UserFirstName
	
Scenario: Check if Admin is able to GET user by USER FirstName with invaid Basic Auth
		Given Admin set the GET request by user firstName with invalid basic auth
		When  Admin sends the GET HTTP request by User FirstName
		Then Admin receives the "401" "Unauthorized" Status code for UserFirstName
		
Scenario: Check if Admin is able to GET user by USER FirstName with No Auth
		Given Admin set the GET request by user firstName with No Auth
		When  Admin sends the GET HTTP request by User FirstName
		Then Admin receives the "401" "Unauthorized" Status code for UserFirstName
