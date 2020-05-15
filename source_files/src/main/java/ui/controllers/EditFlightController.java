package ui.controllers;

import database.operators.*;
import database.tables.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import ui.Util;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * Controls the "Edit Flight" screen. Will allow for editing a row of the flight table in the database given valid input.
 */
public class EditFlightController extends DatabaseManipulatingController {
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();
    private AirportOperator airportOperator = AirportOperator.getInstance();
    private GateOperator gateOperator = GateOperator.getInstance();
    private AircraftOperator aircraftOperator = AircraftOperator.getInstance();
    private FlightStatusTypeOperator flightStatusTypeOperator = FlightStatusTypeOperator.getInstance();

    @FXML private ComboBox<Flight> flightChoiceComboBox;
    @FXML private TextField callsignTextField;
    @FXML private ComboBox<Airline> airlineComboBox;
    @FXML private ComboBox<Airport> departureAirportComboBox;
    @FXML private ComboBox<Airport> arrivalAirportComboBox;
    @FXML private ComboBox<Gate> departureGateComboBox;
    @FXML private ComboBox<Gate> arrivalGateComboBox;
    @FXML private ComboBox<Aircraft> aircraftComboBox;
    @FXML private ComboBox<FlightStatusType> flightStatusComboBox;
    @FXML private DatePicker boardingDateDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
                backButton.getScene().getRoot().requestFocus());

        // Fill Flight Choice ComboBox.
        updateFlightComboBox();
    }

    /**
     * Called when the flight which is to be edited is chosen. Should fill the values of the TextFields and ComboBoxes
     * with what the flight currently has.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void flightChosen(ActionEvent actionEvent) {
        Flight flightChosen = flightChoiceComboBox.getValue();

        callsignTextField.setText(flightChosen.getCallsign());

        airlineComboBox.getItems().clear();
        airlineComboBox.getItems().addAll(airlineOperator.selectAll());
        airlineComboBox.setValue(airlineOperator.selectById(flightChosen.getAirline_id()));

        departureAirportComboBox.getItems().clear();
        departureAirportComboBox.getItems().addAll(airportOperator.selectAll());
        departureAirportComboBox.setValue(airportOperator.selectById(flightChosen.getDeparture_airport_id()));

        arrivalAirportComboBox.getItems().clear();
        arrivalAirportComboBox.getItems().addAll(airportOperator.selectAll());
        arrivalAirportComboBox.setValue(airportOperator.selectById(flightChosen.getArrival_airport_id()));

        departureGateComboBox.setValue(gateOperator.selectById(flightChosen.getDeparture_gate_id()));

        arrivalGateComboBox.setValue(gateOperator.selectById(flightChosen.getArrival_gate_id()));

        aircraftComboBox.getItems().clear();
        aircraftComboBox.getItems().addAll(aircraftOperator.selectManyByAirlineId(flightChosen.getAirline_id()));
        aircraftComboBox.setValue(aircraftOperator.selectById(flightChosen.getAircraft_id()));

        flightStatusComboBox.getItems().clear();
        flightStatusComboBox.getItems().addAll(flightStatusTypeOperator.selectAll());
        flightStatusComboBox.setValue(flightStatusTypeOperator.selectById(flightChosen.getFlight_status_id()));

        boardingDateDatePicker.setValue(flightChosen.getBoarding_date().toLocalDate());
    }

    /**
     * Called when the user chooses a new option in the "Airline" ComboBox. This should change the options
     * in the "Aircraft" ComboBox so that only correct aircrafts can be chosen.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void airlineChosen(ActionEvent actionEvent) {
        Airline airlineChosen = airlineComboBox.getValue();
        aircraftComboBox.getItems().clear();
        aircraftComboBox.setValue(null);
        aircraftComboBox.getItems().addAll(aircraftOperator.selectManyByAirlineId(airlineChosen.getId()));
    }

    /**
     * Called when the user chooses a new option in the "Arrival Airport" ComboBox. This should change the options
     * in the "Arrival Gate" ComboBox so that only correct gates can be chosen.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void arrivalAirportChosen(ActionEvent actionEvent) {
        Airport arrivalAirportChosen = arrivalAirportComboBox.getValue();
        arrivalGateComboBox.getItems().clear();
        arrivalGateComboBox.setValue(null);
        arrivalGateComboBox.getItems().addAll(gateOperator.selectManyByAirportId(arrivalAirportChosen.getId()));
    }


    /**
     * Called when the user chooses a new option in the "Departure Airport" ComboBox. This should change the options
     * in the "Departure Gate" ComboBox so that only correct gates can be chosen.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void departureAirportChosen(ActionEvent actionEvent) {
        Airport departureAirportChosen = departureAirportComboBox.getValue();
        departureGateComboBox.getItems().clear();
        departureGateComboBox.setValue(null);
        departureGateComboBox.getItems().addAll(gateOperator.selectManyByAirportId(departureAirportChosen.getId()));
    }

    /**
     * Called when the "Edit Flight" button is clicked. Handles verifying user input so that a row in the flight table
     * can be edited. Shows success and error messages.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void editFlightButtonClicked(ActionEvent actionEvent) {
        if (callsignTextField.getText() != null && !callsignTextField.getText().isEmpty()
                && airlineComboBox.getValue() != null
                && departureAirportComboBox.getValue() != null
                && arrivalAirportComboBox.getValue() != null
                && departureGateComboBox.getValue() != null
                && arrivalGateComboBox.getValue() != null
                && aircraftComboBox.getValue() != null
                && flightStatusComboBox.getValue() != null
                && boardingDateDatePicker.getValue() != null) {
            // disable buttons until a success/failure is received
            disable();

            Flight flight = new Flight();
            flight.setId(flightChoiceComboBox.getValue().getId());
            flight.setCallsign(callsignTextField.getText());
            flight.setAirline_id(airlineComboBox.getValue().getId());
            flight.setDeparture_airport_id(departureAirportComboBox.getValue().getId());
            flight.setArrival_airport_id(arrivalAirportComboBox.getValue().getId());
            flight.setDeparture_gate_id(departureGateComboBox.getValue().getId());
            flight.setArrival_gate_id(arrivalGateComboBox.getValue().getId());
            flight.setAircraft_id(aircraftComboBox.getValue().getId());
            flight.setFlight_status_id(flightStatusComboBox.getValue().getId());
            flight.setBoarding_date(Date.valueOf(boardingDateDatePicker.getValue()));

            try {
                flightOperator.updateById(flightChoiceComboBox.getValue().getId(), flight);

                // Airline inserted. Clear each component and display success message.
                clearComponents();
                updateFlightComboBox();
                Util.setMessageLabel("Flight edited.", Color.GREEN, messageLabel);
            }
            catch (DuplicateKeyException dke) {
                Util.setMessageLabel("Flight no edited. The callsign and boarding date are unique to a flight.", Color.RED, messageLabel);
            } catch (DataAccessException dae) {
                Util.setMessageLabel("There was a failure while accessing related data in the database. Please try again.", Color.RED, messageLabel);
            } catch (Exception e) {
                Util.setMessageLabel("There was a major failure during this operation.", Color.RED, messageLabel);
            }
            enable();
        } else {
            // All fields must not be null. Display error message.
            Util.setMessageLabel("Flight not edited. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    private void clearComponents() {
        flightChoiceComboBox.setValue(null);
        callsignTextField.clear();
        airlineComboBox.getItems().clear();
        airlineComboBox.setValue(null);
        departureAirportComboBox.getItems().clear();
        departureAirportComboBox.setValue(null);
        arrivalAirportComboBox.getItems().clear();
        arrivalAirportComboBox.setValue(null);
        departureGateComboBox.getItems().clear();
        departureGateComboBox.setValue(null);
        arrivalGateComboBox.getItems().clear();
        arrivalGateComboBox.setValue(null);
        flightStatusComboBox.getItems().clear();
        flightStatusComboBox.setValue(null);
        boardingDateDatePicker.setValue(null);
    }

    private void updateFlightComboBox() {
        flightChoiceComboBox.getItems().addAll(flightOperator.selectAll());
    }
}
