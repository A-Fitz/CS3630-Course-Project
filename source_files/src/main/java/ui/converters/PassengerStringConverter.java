package ui.converters;

import database.tables.Passenger;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and Passenger data objects. Useful for UI display of objects.
 */
public class PassengerStringConverter extends StringConverter<Passenger> {

    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param passenger The given object.
     * @return The String.
     */
    @Override
    public String toString(Passenger passenger) {
        StringBuilder sb = new StringBuilder();
        if (passenger.getPassport_number() != null) {
            sb.append("Passport Number: [");
            sb.append(passenger.getPassport_number());
            sb.append("] ");
        }
        sb.append("First Name: [");
        sb.append(passenger.getFirst_name());
        sb.append("] ");
        sb.append("Middle Name: [");
        sb.append(passenger.getMiddle_name());
        sb.append("] ");
        sb.append("Last Name: [");
        sb.append(passenger.getLast_name());
        sb.append("] ");
        sb.append("Email: [");
        sb.append(passenger.getEmail());
        sb.append("] ");
        sb.append("Address: [");
        sb.append(passenger.getAddress());
        sb.append("] ");
        sb.append("Phone: [");
        sb.append(passenger.getPhone());
        sb.append("] ");
        sb.append("Birth Date: [");
        sb.append(passenger.getBirth_date().toString());
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
    public Passenger fromString(String s) {
        return null;
    }
}
