package ui.converters;

import database.operators.BaggageStatusTypeOperator;
import database.operators.FlightOperator;
import database.operators.PassengerOnFlightOperator;
import database.operators.PassengerOperator;
import database.tables.Baggage;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and Baggage data objects. Useful for UI display of objects.
 */
public class BaggageStringConverter extends StringConverter<Baggage> {
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private BaggageStatusTypeOperator baggageStatusTypeOperator = BaggageStatusTypeOperator.getInstance();
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();
    private PassengerOnFlightOperator passengerOnFlightOperator = PassengerOnFlightOperator.getInstance();

    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param baggage The given object.
     * @return The String.
     */
    @Override
    public String toString(Baggage baggage) {
        StringBuilder sb = new StringBuilder();
        sb.append("Flight Callsign: [");
        sb.append(flightOperator.selectById(passengerOnFlightOperator.selectById(baggage.getPassenger_on_flight_id()).getFlight_id()).getCallsign());
        sb.append("] ");
        if (passengerOperator.selectById(passengerOnFlightOperator.selectById(baggage.getPassenger_on_flight_id()).getPassenger_id()).getPassport_number() != null) {
            sb.append("Passenger Passport Number: [");
            sb.append(passengerOperator.selectById(passengerOnFlightOperator.selectById(baggage.getPassenger_on_flight_id()).getPassenger_id()).getPassport_number());
            sb.append("] ");
        } else {
            sb.append("Passenger First Name: [");
            sb.append(passengerOperator.selectById(passengerOnFlightOperator.selectById(baggage.getPassenger_on_flight_id()).getPassenger_id()).getFirst_name());
            sb.append("] ");
            sb.append("Passenger Last Name: [");
            sb.append(passengerOperator.selectById(passengerOnFlightOperator.selectById(baggage.getPassenger_on_flight_id()).getPassenger_id()).getLast_name());
            sb.append("] ");
            sb.append("Passenger Birthdate: [");
            sb.append(passengerOperator.selectById(passengerOnFlightOperator.selectById(baggage.getPassenger_on_flight_id()).getPassenger_id()).getBirth_date().toString());
            sb.append("] ");
        }
        sb.append("Weight: [");
        sb.append(baggage.getWeight());
        sb.append("] ");
        sb.append("Status: [");
        sb.append(baggageStatusTypeOperator.selectById(baggage.getBaggage_status_id()).getTitle());
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
    public Baggage fromString(String s) {
        return null;
    }
}
