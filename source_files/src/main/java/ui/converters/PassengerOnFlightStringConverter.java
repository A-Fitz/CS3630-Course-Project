package ui.converters;

import database.tables.Passenger;
import database.tables.PassengerOnFlight;
import javafx.util.StringConverter;

public class PassengerOnFlightStringConverter extends StringConverter<PassengerOnFlight> {

    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param passengerOnFlight The given object.
     * @return The String.
     */
    @Override
    public String toString(PassengerOnFlight passengerOnFlight) {
        StringBuilder sb = new StringBuilder();
        if (passengerOnFlight.getFlight_id() != null) {
            sb.append("Flight ID: [");
            sb.append(passengerOnFlight.getFlight_id());
            sb.append("] ");
        }
        sb.append("Passenger ID: [");
        sb.append(passengerOnFlight.getPassenger_id());
        sb.append("] ");

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
    public PassengerOnFlight fromString(String s) {
        return null;
    }
}
