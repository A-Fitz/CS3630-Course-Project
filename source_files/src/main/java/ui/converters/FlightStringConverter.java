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
     *
     * @param flight The given object.
     * @return The String.
     */
    @Override
    public String toString(Flight flight) {
        return "Callsign: [" +
                flight.getCallsign() +
                "] " +
                "Airline: [" +
                airlineOperator.selectById(flight.getAirline_id()).getName() +
                "] " +
                "Departure Airport: [" +
                airportOperator.selectById(flight.getDeparture_airport_id()).getIata_code() +
                "] " +
                "Arrival Airport: [" +
                airportOperator.selectById(flight.getArrival_airport_id()).getIata_code() +
                "] " +
                "Departure Gate: [" +
                gateOperator.selectById(flight.getDeparture_gate_id()).getGate_code() +
                "] " +
                "Arrival Gate: [" +
                gateOperator.selectById(flight.getArrival_gate_id()).getGate_code() +
                "] " +
                "Aircraft: [" +
                aircraftOperator.selectById(flight.getAircraft_id()).getSerial_number() +
                "] " +
                "Status: [" +
                flightStatusTypeOperator.selectById(flight.getFlight_status_id()).getTitle() +
                "] " +
                "Boarding Date: [" +
                flight.getBoarding_date().toString() +
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
    public Flight fromString(String s) {
        return null;
    }
}
