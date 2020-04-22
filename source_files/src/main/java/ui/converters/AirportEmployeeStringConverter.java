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
        StringBuilder sb = new StringBuilder();
        sb.append("Airport: [");
        sb.append(airportOperator.selectById(airportEmployee.getAirport_id()).getIata_code());
        sb.append("] ");
        sb.append("Job: [");
        sb.append(airportJobTypeOperator.selectById(airportEmployee.getJob_id()).getTitle());
        sb.append("] ");
        sb.append("First Name: [");
        sb.append(airportEmployee.getFirst_name());
        sb.append("] ");
        sb.append("Middle Name: [");
        sb.append(airportEmployee.getMiddle_name());
        sb.append("] ");
        sb.append("Last Name: [");
        sb.append(airportEmployee.getLast_name());
        sb.append("] ");
        sb.append("Email: [");
        sb.append(airportEmployee.getEmail());
        sb.append("] ");
        sb.append("Address: [");
        sb.append(airportEmployee.getAddress());
        sb.append("] ");
        sb.append("Phone: [");
        sb.append(airportEmployee.getPhone());
        sb.append("] ");
        sb.append("Birth Date: [");
        sb.append(airportEmployee.getBirth_date().toString());
        sb.append("]");

        return sb.toString();
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
