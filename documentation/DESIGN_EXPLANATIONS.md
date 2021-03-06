
## Design Explanations

1. [System] EditFlightController and other future controllers - Austin FitzGerald
- My rationale for using ComboBox dropdowns to select the Flight and Flight information:
In a real Airport Management System there would be hundreds of thousands of data entries for flights, airports, airlines, gates, etc. So using drop downs would be ridiculous as it would take ages to find what you need. In that case you would want to enter each piece of information, and hope that the error detection would figure out if you were wrong. Well, this is a small system meant to exercise our abilities in database design and implementation, so we don't need to worry about hundreds of thousands of data entries. We can avoid a complex error detection system and instead use dropdowns to select our data.
- So, controller design should be as follows. For adding a new row to a table, such as Flight or Passenger, if that table contains foreign key relationships, do not just have the user enter them. Instead, show a ComboBox dropdown containing the data that they can choose. That way we don't have to worry about entering an invalid id for a foreign key relationship. The ComboBox can directly contain an object, where a StringConverter will automatically do a toString() type method to display the object's data (see EditFlightControler).
- Yes, this is slow. Part of it is JavaFX, part of it is the database. Remember that the database is hosted on our professor's computer and we have not added indices yet.

2. [System] Database package design - Austin FitzGerald
- The database package includes a subpackage which includes classes which contain data from a specific row in a table, as well as other information that a user may like to read. This user-readable information is stored so that our data objects can be used directly in views. Both row-specific information and user-readable information are created with extractors. Each class in this subpackage implements an interface called DatabaseObject, which defines methods that each class much have.
- The extractors subpackage includes classes which have a single purpose of creating a database representative object from the data in a Result Set. The Result Sets are created by Operator functions.
- The operators subpackage includes classes which directly interact with the database connection. All classes follow the singleton design pattern, just like the DatabaseConnection class. Each operator class implements an interface which defines behavior for select, insert, update, and delete SQL queries in the class-specific table. The interface requires that these methods only interact with a specific DatabaseObject class as defined by a generic type. These operator classes are used by controllers to manipulate the view, and allow users to manipulate the database with pre-structured queries.

3. [Database] Audit functionality - Austin FitzGerald on behalf of team
- Users of an Airport Management System may want some automatic logging capabilities in case of audit or backup. We wanted to implement automatic logging on deletion of rows for certain tables (baggage, airline_employee, airline_employee_on_flight, passenger, pasenger_on_flight, ticket).
- We have created procedures which will save all attributes of a deleted row into a specific audit table. These procedures are run when a row is deleted.

4. [Database] Indexes - Austin FitzGerald on behalf of team
- We have added indexes on to all foreign keys in each table. Our system requires many select statements to all needed information in most views, so we were having performance issues related to selects. These indexes should increase performance on selects involving foreign keys.