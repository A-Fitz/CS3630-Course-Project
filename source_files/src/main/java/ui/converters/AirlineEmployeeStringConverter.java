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
        StringBuilder sb = new StringBuilder();
        sb.append("Airline: [");
        sb.append(airlineOperator.selectById(airlineEmployee.getAirline_id()).getName());
        sb.append("] ");
        sb.append("Job: [");
        sb.append(airlineJobTypeOperator.selectById(airlineEmployee.getJob_id()).getTitle());
        sb.append("] ");
        sb.append("First Name: [");
        sb.append(airlineEmployee.getFirst_name());
        sb.append("] ");
        sb.append("Middle Name: [");
        sb.append(airlineEmployee.getMiddle_name());
        sb.append("] ");
        sb.append("Last Name: [");
        sb.append(airlineEmployee.getLast_name());
        sb.append("] ");
        sb.append("Email: [");
        sb.append(airlineEmployee.getEmail());
        sb.append("] ");
        sb.append("Address: [");
        sb.append(airlineEmployee.getAddress());
        sb.append("] ");
        sb.append("Phone: [");
        sb.append(airlineEmployee.getPhone());
        sb.append("] ");
        sb.append("Birth Date: [");
        sb.append(airlineEmployee.getBirth_date().toString());
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
    public AirlineEmployee fromString(String s) {
        return null;
    }
}
