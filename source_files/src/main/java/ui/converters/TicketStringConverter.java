package ui.converters;

import database.operators.*;
import database.tables.base.Ticket;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and Ticket data objects. Useful for UI display of objects.
 */
public class TicketStringConverter extends StringConverter<Ticket> {
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private TicketStatusTypeOperator ticketStatusTypeOperator = TicketStatusTypeOperator.getInstance();
    private SeatClassTypeOperator seatClassTypeOperator = SeatClassTypeOperator.getInstance();
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();
    private PassengerOnFlightOperator passengerOnFlightOperator = PassengerOnFlightOperator.getInstance();

    /**
     * Creates a String containing the information which can identify a given object.
     *
     * @param ticket The given object.
     * @return The String.
     */
    @Override
    public String toString(Ticket ticket) {
        StringBuilder sb = new StringBuilder();
        sb.append("Flight Callsign: [");
        sb.append(flightOperator.selectById(passengerOnFlightOperator.selectById(ticket.getPassenger_on_flight_id()).getFlight_id()).getCallsign());
        sb.append("] ");
        if (passengerOperator.selectById(passengerOnFlightOperator.selectById(ticket.getPassenger_on_flight_id()).getPassenger_id()).getPassport_number() != null) {
            sb.append("Passenger Passport Number: [");
            sb.append(passengerOperator.selectById(passengerOnFlightOperator.selectById(ticket.getPassenger_on_flight_id()).getPassenger_id()).getPassport_number());
            sb.append("] ");
        } else {
            sb.append("Passenger First Name: [");
            sb.append(passengerOperator.selectById(passengerOnFlightOperator.selectById(ticket.getPassenger_on_flight_id()).getPassenger_id()).getFirst_name());
            sb.append("] ");
            sb.append("Passenger Last Name: [");
            sb.append(passengerOperator.selectById(passengerOnFlightOperator.selectById(ticket.getPassenger_on_flight_id()).getPassenger_id()).getLast_name());
            sb.append("] ");
            sb.append("Passenger Birthdate: [");
            sb.append(passengerOperator.selectById(passengerOnFlightOperator.selectById(ticket.getPassenger_on_flight_id()).getPassenger_id()).getBirth_date().toString());
            sb.append("] ");
        }
        sb.append("Price: [");
        sb.append(ticket.getPrice());
        sb.append("] ");
        sb.append("Seat: [");
        sb.append(ticket.getSeat());
        sb.append("] ");
        sb.append("Seat Class: [");
        sb.append(seatClassTypeOperator.selectById(ticket.getSeat_class_id()).getTitle());
        sb.append("] ");
        sb.append("Status: [");
        sb.append(ticketStatusTypeOperator.selectById(ticket.getTicket_status_id()).getTitle());
        sb.append("] ");
        sb.append("Purchase Date/Time: [");
        sb.append(ticket.getPurchase_timestamp().toString());
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
    public Ticket fromString(String s) {
        return null;
    }
}
