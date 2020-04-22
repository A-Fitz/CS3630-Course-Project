
## Design Explanations

1. EditFlightController and other future controllers - Austin FitzGerald
- My rationale for using ComboBox dropdowns to select the Flight and Flight information:
In a real Airport Management System there would be hundreds of thousands of data entries for flights, airports, airlines, gates, etc. So using drop downs would be ridiculous as it would take ages to find what you need. In that case you would want to enter each piece of information, and hope that the error detection would figure out if you were wrong. Well, this is a small system meant to exercise our abilities in database design and implementation, so we don't need to worry about hundreds of thousands of data entries. We can avoid a complex error detection system and instead use dropdowns to select our data.
- So, controller design should be as follows. For adding a new row to a table, such as Flight or Passenger, if that table contains foreign key relationships, do not just have the user enter them. Instead, show a ComboBox dropdown containing the data that they can choose. That way we don't have to worry about entering an invalid id for a foreign key relationship. The ComboBox can directly contain an object, where a StringConverter will automatically do a toString() type method to display the object's data (see EditFlightControler).
- Yes, this is slow. Part of it is JavaFX, part of it is the database. Remember that the database is hosted on our professor's computer and we have not added indices yet.
