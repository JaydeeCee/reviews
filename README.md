# Overview
The project contains solutions to retrieving Youtube comments via Youtube API and Amazon Reviews via Web scaping technology.
Built with Spring Boot Web framework. It relies heavily on JPA and Hibernate for database operations especially for retrieving thousands of Amazon reviews 
so as not to overload the server memory.

##Maven
Functionality of this package is contained in the maven pom.xml

###Installation:
Install maven-built jar as a service. Application runs in Tomcat, port 8101.

###MySQL:
Application uses MySQL Database. Runs on port 3306.
Database name = reviews
Please create a database in your MySQL before starting application to avoid JdbcConnection Exception.
Username and Passwords are required. They are set in the application.properties file.

###Author
Ooreade Adeniran
https://github.com/JaydeeCee


