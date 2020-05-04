package ui.converters;

import database.operators.AirportJobTypeOperator;
import database.operators.AirportOperator;
import database.tables.AirportEmployee;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and AirportEmployee data objects. Useful for UI display of objects.
 */
public class AirportEmployeeStringConverter extends StringConverter<AirportEmployee> {
    private AirportOperator airportOperator = AirportOperator.getInstance();
    private AirportJobTypeOperator airportJobTypeOperator = AirportJobTypeOperator.getInstance();

    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param airportEmployee The given object.
     * @return The String.
     */
    @Override
    public String toString(AirportEmployee airportEmployee) {

        return "Airport: [" +
                airportOperator.selectById(airportEmployee.getAirport_id()).getIata_code() +
                "] " +
                "Job: [" +
                airportJobTypeOperator.selectById(airportEmployee.getJob_id()).getTitle() +
                "] " +
                "First Name: [" +
                airportEmployee.getFirst_name() +
                "] " +
                "Middle Name: [" +
                airportEmployee.getMiddle_name() +
                "] " +
                "Last Name: [" +
                airportEmployee.getLast_name() +
                "] " +
                "Email: [" +
                airportEmployee.getEmail() +
                "] " +
                "Address: [" +
                airportEmployee.getAddress() +
                "] " +
                "Phone: [" +
                airportEmployee.getPhone() +
                "] " +
                "Birth Date: [" +
                airportEmployee.getBirth_date().toString() +
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
    public AirportEmployee fromString(String s) {
        return null;
    }
}
