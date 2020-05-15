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

public class AddFlightController extends DatabaseManipulatingController {
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();
    private AirportOperator airportOperator = AirportOperator.getInstance();
    private GateOperator gateOperator = GateOperator.getInstance();
    private AircraftOperator aircraftOperator = AircraftOperator.getInstance();
    private FlightStatusTypeOperator flightStatusTypeOperator = FlightStatusTypeOperator.getInstance();


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

        // Fill Airline Choice ComboBox.
        airlineComboBox.getItems().clear();
        airlineComboBox.getItems().addAll(airlineOperator.selectAll());

        departureAirportComboBox.getItems().clear();
        departureAirportComboBox.getItems().addAll(airportOperator.selectAll());

        arrivalAirportComboBox.getItems().clear();
        arrivalAirportComboBox.getItems().addAll(airportOperator.selectAll());

        flightStatusComboBox.getItems().clear();
        flightStatusComboBox.getItems().addAll(flightStatusTypeOperator.selectAll());
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
     * Called when the "Edit Flight" button is clicked. Handles verifying user input so that a row in the flight table
     * can be edited. Shows success and error messages.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addFlightButtonClicked(ActionEvent actionEvent) {
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
                flightOperator.insert(flight);

                // Flight inserted. Clear each component and display success message.
                clearComponents();
                Util.setMessageLabel("Flight added.", Color.GREEN, messageLabel);
                enable();
            }
            catch (DuplicateKeyException dke) {
                Util.setMessageLabel("Flight not added. The callsign and boarding date are unique to a flight.", Color.RED, messageLabel);
            } catch (DataAccessException dae) {
                Util.setMessageLabel("There was a failure while accessing related data in the database. Please try again.", Color.RED, messageLabel);
            } catch (Exception e) {
                Util.setMessageLabel("There was a major failure during this operation.", Color.RED, messageLabel);
            }
            enable();
        } else {
            // All fields must not be null. Display error message.
            Util.setMessageLabel("Flight not added. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    private void clearComponents() {
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


}
