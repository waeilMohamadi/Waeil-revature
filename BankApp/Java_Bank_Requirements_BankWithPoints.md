# Bank

## Description

   The Bank app is a console-based application that simulates banking operations. A customer can apply for an account, view their balance, and make withdrawals and deposits. An employee can approve or deny accounts and view account balances for their customers.
	

## Requirements
1. Functionality should reflect the below user stories.
2. Data is stored in a database.
3. Data Access is performed through the use of JDBC in a data layer consisting of Data Access Objects.
4. All input is received using the java.util.Scanner class.
5. Log4j is implemented to log events to a file.
6. A minimum of 10 JUnit test is written to test some functionality.
7. using Maven


## User Stories
Total Points: 23 points

* As a user, I can login.
	* 2 points
* As a customer, I can apply for a new bank account with a starting balance.
	* 3 points
* As a customer, I can view the balance of a specific account.
	* 1 point
* As a customer, I can make a withdrawal or deposit to a specific account.
	* 2 points
* As the system, I reject invalid transactions.
	* Ex:
		* A withdrawal that would result in a negative balance.
		* A deposit or withdrawal of negative money.
	* 2 points
* As an employee, I can approve or reject an account.
	* 2 points
* As an employee, I can view a customer's bank accounts.
	* 1 point
* As a user, I can register for a customer account.
	* 3 points
* As a customer, I can post a money transfer to another account.
	* 3 points
* As a customer, I can accept a money transfer from another account.
	* 2 points
* As an employee, I can view a log of all transactions.
	* 2 points

