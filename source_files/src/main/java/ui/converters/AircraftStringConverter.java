package ui.converters;

import database.operators.AirlineOperator;
import database.tables.Aircraft;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and Aircraft data objects. Useful for UI display of objects.
 */
public class AircraftStringConverter extends StringConverter<Aircraft> {
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();

    /**
     * Creates a String containing the information which can identify a given object.
     * @param aircraft The given object.
     * @return The String.
     */
    @Override
    public String toString(Aircraft aircraft) {
        StringBuilder sb = new StringBuilder();
        sb.append("Airline: [");
        sb.append(airlineOperator.selectById(aircraft.getAirline_id()).getName());
        sb.append("] ");
        sb.append("Serial #: [");
        sb.append(aircraft.getSerial_number());
        sb.append("] ");
        sb.append("Make: [");
        sb.append(aircraft.getMake());
        sb.append("] ");
        sb.append("Model: [");
        sb.append(aircraft.getModel());
        sb.append("] ");
        sb.append("Year: [");
        sb.append(aircraft.getYear());
        sb.append("] ");
        sb.append("Capacity: [");
        sb.append(aircraft.getCapacity());
        sb.append("]");

        return sb.toString();
    }

    /**
     * Usually used to create an object from identifiable information. In this case we do not need to use it
     * as we have specific fields for each table's columns.
     * @param s Not used.
     * @return null
     */
    @Override
    public Aircraft fromString(String s) {
        return null;
    }
}
