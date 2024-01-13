# BloodBankApp-MySQL
A Blood Bank application developed with MySql as backend and Window Builder in Java 8 for the UI

**Backend Schema**
OVERVIEW
The application created is a Blood Bank application that allows users to register themselves as donors, perform various CRUD(Insert, Update, Delete, View) operations and store data into the database. Also there exists functionality to add blood donation booking slot for future and view already existing donation records from the database. Information for various bloodbanks with their locations are also fetched from the database.

EER Diagram
![image](https://github.com/rjrnaik/BloodBankApp-MySQL/assets/50843052/5441878e-047d-48db-bf61-57c3b7d29958)

Tables used:
1) location(PK: donorId)
2) bloodbank (PK: bloodbankId) – has FK: locationId
3) donation (PK: donationId) – has FKs: donorId & bloodbankId
4) donor (PK: donorId) – has FK: bloodTypeId
5) bloodType (PK: bloodTypeId)
1-many relationships
1) location - -< - bloodbank
2) bloodbank - -< - donation
3) donor - -< - donation
4) bloodType - -< - donor

**Presentation Layer**
Overview The Presentation layer of the Blood bank application has been created as a Graphic User Interface (GUI) built using Window builder(Java Swing) in Eclipse and uses Java 1.8.
The application interface has 4 different UI screens for accepting, modifying, searching and displaying data to and from MySQL database created.
The entire UI is created by coding in java. Three external jar files required as part of the execution:
1) mysql-connector-j-8.0.31.jar
2) rs2xml.jar
3) jcalendar-1.4.jar
mysql-connector-j-8.0.31.jar and rs2xml.jar files are used to establish connectivity with the MySQL database and ensure security.
jcalendar-1.4.jar is used to add calendar / date picker UI element for the Donation entry page. The source code for the same will be shared and attached as 4 source code files (.java) in SLATE for better readability.
The application is free from SQL injection as we have used Prepared statement and passed all data in the query using by parameterizing the same.
Exception handling is also performed at all points in the code where an error or an exception may be encountered.
