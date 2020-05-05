package ui.converters;

import database.tables.base.Airline;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and Airline data objects. Useful for UI display of objects.
 */
public class AirlineStringConverter extends StringConverter<Airline> {
    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param airline The given object.
     * @return The String.
     */
    @Override
    public String toString(Airline airline) {

        return "Name: [" +
                airline.getName() +
                "] " +
                "Abbreviation: [" +
                airline.getAbbreviation() +
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
    public Airline fromString(String s) {
        return null;
    }
}
