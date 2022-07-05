# Java_Project
## Table of contents
* [About info](#about-info)
* [Requirements](#requirements-met)
* [Technologies](#technologies)
* [Status](#status)

## About info
* The topic of this Java application is a book lending system, that has the following features:
  * logging in as a librarian/partner/user
  * lending books;
  * returning books;
  * adding, removing and viewing books/sections/users/partners.
* The 3 types of users are:
  * user - a type of user that only has access to viewing lent/returned books;
  * partner - a type of user that only has access to adding, viewing added books;
  * librarian - the user that has access to lending/returning books and adding, viewing, removing sections/users/partners.
* The main rules of the application are:
  * a user is charged 2 ron/day for lending a book;
  * a user can keep a book for maximum 60 days;
  * after exceeding the return date, the user will be charged an additional 3 ron/day;
  * after returning the book earlier, the user will earn a 10% discount code, that is available for maximum 60 days;
  * a user can only use 1 discount/lent book;
  * every book is unique, meaning there is only one print available for each;
  * the username of the librarian is "admin";
  * the password of the librarian is "admin";
  * when creating a new account, the librarian cannot set its password; for security reasons, the user/partner receives a default password that consists of all the characters before the @ in their email;
  * the default password can be changed by the user/partner when logging in for the first time.

## Requirements
## Stage I

### Assignment definition

Select a system to be implemented that allows at least 10 actions/queries to be performed on at least 8 types of objects.

### Implementation 

Implement a project using the Java language that complies to the requirements above. 

The application will:

* include simple classes with private/protected attributes and methods
* include at least 2 different collections capable of administering the objects in the application
* use inheritance for some of the classes used within the collections
* at least one service class that exposes the system's operations 
* a main class that calls the service's methods 

## Stage II

### Persistent storage 

Extend the project from the 1st stage by persisting the data using files:

* CSV files will be used to store at least 4 types of objects from the first stage. Each column in the file is separated with a comma. Example: `name,surname,age`
* Generic singleton services will be created for reading and writing from/to files
* At system startup, the data will be automatically loaded from the files.

### Auditing

An auditing service will need to be created that will log to a CSV file each time an action from the first stage is performed. Structure of the file: `name_of_action,timestamp`.

## Stage III 

### jdbc.Database persistence

Replace the services created in the second stage with others that use JDBC to store the data in a database of your choosing.

A service will be created that will expose create/read/update/delete operations for at least 4 of the defined classes.

## Technologies
* Programing languages: SQL, Java;
* IDEs: Oracle SQL Developer, IntelliJ IDEA.

## Status
Although all the project requirements were met, the user interface is not complete.
