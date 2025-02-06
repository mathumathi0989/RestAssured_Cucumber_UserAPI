#Author: Mathumathi


Feature: GetAllUsers


  Scenario Outline: Get All Users request validations
    Given Admin set the "<requestType>" with "<endpoint>" and with "<authType>"
    When Admin sends HTTPS Request 
    Then Admin receives <statusCode> and "<statusText>"

    Examples: 
     |requestType |authType	 | endpoint 	 | statusCode 	| statusText 				  |
     | GET		| Basic		 | valid			 |     200 			|    OK								|
     | GET		| Basic 	 | Without 		 |     404			| Not Found  				  |
     | GET		| Basic		 | invalid		 |		 404			| Not Found						|
     | POST		| Basic		 | valid			 |		405				|	Method Not Allowed 	|
     | GET		| No Auth  | valid			 |  	401				|	Unauthorized				|
     | GET		| Invalid  | valid			 | 	  401				| Unauthorized				|
     
      
