package ui.converters;

import database.operators.AirlineJobTypeOperator;
import database.operators.AirlineOperator;
import database.tables.AirlineEmployee;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and AirlineEmployee data objects. Useful for UI display of objects.
 */
public class AirlineEmployeeStringConverter extends StringConverter<AirlineEmployee> {
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();
    private AirlineJobTypeOperator airlineJobTypeOperator = AirlineJobTypeOperator.getInstance();

    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param airlineEmployee The given object.
     * @return The String.
     */
    @Override
    public String toString(AirlineEmployee airlineEmployee) {

        return "Airline: [" +
                airlineOperator.selectById(airlineEmployee.getAirline_id()).getName() +
                "] " +
                "Job: [" +
                airlineJobTypeOperator.selectById(airlineEmployee.getJob_id()).getTitle() +
                "] " +
                "First Name: [" +
                airlineEmployee.getFirst_name() +
                "] " +
                "Middle Name: [" +
                airlineEmployee.getMiddle_name() +
                "] " +
                "Last Name: [" +
                airlineEmployee.getLast_name() +
                "] " +
                "Email: [" +
                airlineEmployee.getEmail() +
                "] " +
                "Address: [" +
                airlineEmployee.getAddress() +
                "] " +
                "Phone: [" +
                airlineEmployee.getPhone() +
                "] " +
                "Birth Date: [" +
                airlineEmployee.getBirth_date().toString() +
                "]";
    }

    /**
     * Usually used to create an object from identifiable information. In this case we do not need to use it
     * as we have specific fields for each table's columns.
     *
     * @param s Not used.
     * @return null
     */
    @Override
    public AirlineEmployee fromString(String s) {
        return null;
    }
}
