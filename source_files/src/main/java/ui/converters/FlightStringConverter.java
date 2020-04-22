package ui.converters;

import database.operators.*;
import database.tables.Flight;
import javafx.util.StringConverter;

/**
 * Defines conversion behavior between strings and Passenger data objects. Useful for UI display of objects.
 */
public class FlightStringConverter extends StringConverter<Flight> {
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();
    private AirportOperator airportOperator = AirportOperator.getInstance();
    private GateOperator gateOperator = GateOperator.getInstance();
    private AircraftOperator aircraftOperator = AircraftOperator.getInstance();
    private FlightStatusTypeOperator flightStatusTypeOperator = FlightStatusTypeOperator.getInstance();

    /**
     * Creates a String containing the information which can identify a given object.
     * @param flight The given object.
     * @return The String.
     */
    @Override
    public String toString(Flight flight) {
        StringBuilder sb = new StringBuilder();
        sb.append("Callsign: [");
        sb.append(flight.getCallsign());
        sb.append("] ");
        sb.append("Airline: [");
        sb.append(airlineOperator.selectById(flight.getAirline_id()).getName());
        sb.append("] ");
        sb.append("Departure Airport: [");
        sb.append(airportOperator.selectById(flight.getDeparture_airport_id()).getIata_code());
        sb.append("] ");
        sb.append("Arrival Airport: [");
        sb.append(airportOperator.selectById(flight.getArrival_airport_id()).getIata_code());
        sb.append("] ");
        sb.append("Departure Gate: [");
        sb.append(gateOperator.selectById(flight.getDeparture_gate_id()).getGate_code());
        sb.append("] ");
        sb.append("Arrival Gate: [");
        sb.append(gateOperator.selectById(flight.getArrival_gate_id()).getGate_code());
        sb.append("] ");
        sb.append("Aircraft: [");
        sb.append(aircraftOperator.selectById(flight.getAircraft_id()).getSerial_number());
        sb.append("] ");
        sb.append("Status: [");
        sb.append(flightStatusTypeOperator.selectById(flight.getFlight_status_id()).getTitle());
        sb.append("] ");
        sb.append("Boarding Date: [");
        sb.append(flight.getBoarding_date().toString());
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
    public Flight fromString(String s) {
        return null;
    }
}
