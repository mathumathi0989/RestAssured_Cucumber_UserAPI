#Author: Mathumathi

 Feature: Create User API
 

Scenario: Check if Admin is able to create a user with all valid credentials
    Given Admin set the POST request with the valid request body
    When Admin sends HTTPS Request and request Body with endpoint
    Then Admin receives "201" "Created" Status

Scenario: Check if Admin is able to create a user with only mandatory fields
		Given Admin set the POST request with the valid request body
		When Admin sends HTTPS Request and request Body with mandatory
		Then Admin receives "201" "Created" Status for create user

Scenario: Check if Admin is able to create a user with invalid endpoint
    Given Admin set the POST request with the valid request body
    When Admin sends HTTPS Request and request Body with invalid endpoint
    Then Admin receives "404" "Not Found" Status		
    
Scenario: Check if Admin is able to create a user with invalid content Type
    Given Admin set the POST request with the invalid contentType
    When Admin sends HTTPS Request and request Body with invalid content type
    Then Admin receives "415" "Unsupported Media Type" Status		
    
Scenario: Check if Admin is able to create a user with invalid request type
		 Given Admin set the POST request with the valid request body
		 When Admin sends HTTPS Request and request Body with invalid request type
		 Then Admin receives "405" "Not Allowed" Status
	 
Scenario: Check if Admin is able to create a user with same contact number
		 Given Admin set the POST request with the valid request body
		 When Admin sends HTTPS Request and request Body with same contact number
		 Then Admin receives "409" "Conflict" Status with contact message
 		 
Scenario: Check if Admin is able to create a user with same email
		 Given Admin set the POST request with the valid request body
		 When Admin sends HTTPS Request and request Body with same email
		 Then Admin receives "409" "Conflict" Status with email message	 

Scenario: Check if Admin is able to create a user with No Auth
    Given Admin set the POST request with the valid request body with no Auth
    When Admin sends HTTPS Request and request Body with no or invalid auth
    Then Admin receives "401" "Unauthorized" Status

Scenario: Check if Admin is able to create a user with invalid basic auth
    Given Admin set the POST request with the valid request body with invalid basic Auth
    When Admin sends HTTPS Request and request Body with no or invalid auth
    Then Admin receives "401" "Unauthorized" Status		 



Scenario: Check if Admin is able to create a user with first name as numeric 
    Given Admin set the POST request
    When Admin sends HTTPS Request and request Body with first name as numeric 
    Then Admin receives "400" "Bad Request" Status with error message		

		
		