package ui.converters;

import database.tables.Airport;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and Airport data objects. Useful for UI display of objects.
 */
public class AirportStringConverter extends StringConverter<Airport> {
    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param airport The given object.
     * @return The String.
     */
    @Override
    public String toString(Airport airport) {

        return "Name: [" +
                airport.getName() +
                "] " +
                "IATA: [" +
                airport.getIata_code() +
                "] " +
                "City: [" +
                airport.getCity() +
                "] " +
                "Country: [" +
                airport.getCountry() +
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
    public Airport fromString(String s) {
        return null;
    }
}
