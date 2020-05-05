package ui.converters;

import database.operators.AirlineOperator;
import database.tables.base.Aircraft;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and Aircraft data objects. Useful for UI display of objects.
 */
public class AircraftStringConverter extends StringConverter<Aircraft> {
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();

    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param aircraft The given object.
     * @return The String.
     */
    @Override
    public String toString(Aircraft aircraft) {

        return "Airline: [" +
                airlineOperator.selectById(aircraft.getAirline_id()).getName() +
                "] " +
                "Serial #: [" +
                aircraft.getSerial_number() +
                "] " +
                "Make: [" +
                aircraft.getMake() +
                "] " +
                "Model: [" +
                aircraft.getModel() +
                "] " +
                "Year: [" +
                aircraft.getYear() +
                "] " +
                "Capacity: [" +
                aircraft.getCapacity() +
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
    public Aircraft fromString(String s) {
        return null;
    }
}
