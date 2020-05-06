package ui.controllers;

import database.operators.*;
import database.tables.base.*;
import database.tables.information.AircraftInformation;
import database.tables.information.FlightInformation;
import database.tables.information.GateInformation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;
import ui.Util;
import ui.converters.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * Controls the "Edit Flight" screen. Will allow for editing a row of the flight table in the database given valid input.
 */
public class EditFlightController implements Initializable {
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();
    private AirportOperator airportOperator = AirportOperator.getInstance();
    private GateOperator gateOperator = GateOperator.getInstance();
    private AircraftOperator aircraftOperator = AircraftOperator.getInstance();
    private FlightStatusTypeOperator flightStatusTypeOperator = FlightStatusTypeOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Label messageLabel;
    @FXML private ComboBox<FlightInformation> flightChoiceComboBox;
    @FXML private TextField callsignTextField;
    @FXML private ComboBox<Airline> airlineComboBox;
    @FXML private ComboBox<Airport> departureAirportComboBox;
    @FXML private ComboBox<Airport> arrivalAirportComboBox;
    @FXML private ComboBox<GateInformation> departureGateComboBox;
    @FXML private ComboBox<GateInformation> arrivalGateComboBox;
    @FXML private ComboBox<AircraftInformation> aircraftComboBox;
    @FXML private ComboBox<FlightStatusType> flightStatusComboBox;
    @FXML private DatePicker boardingDateDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();

            airlineComboBox.setConverter(new AirlineStringConverter());
            departureAirportComboBox.setConverter(new AirportStringConverter());
            arrivalAirportComboBox.setConverter(new AirportStringConverter());
            flightStatusComboBox.setConverter(new FlightStatusTypeStringConverter());
        });

        // Fill Flight Choice ComboBox.
        flightChoiceComboBox.getItems().addAll(flightOperator.getInformationForAll());
    }

    /**
     * Called when the flight which is to be edited is chosen. Should fill the values of the TextFields and ComboBoxes
     * with what the flight currently has.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void flightChosen(ActionEvent actionEvent) {
        Flight flightChosen = flightOperator.selectById(flightChoiceComboBox.getValue().getId());

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

        departureGateComboBox.setValue(gateOperator.getInformationFromId(flightChosen.getDeparture_gate_id()));

        arrivalGateComboBox.setValue(gateOperator.getInformationFromId(flightChosen.getArrival_gate_id()));

        aircraftComboBox.getItems().clear();
        aircraftComboBox.getItems().addAll(aircraftOperator.getInformationFromAirlineId(flightChosen.getAirline_id()));
        aircraftComboBox.setValue(aircraftOperator.getInformationFromId(flightChosen.getAircraft_id()));

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
        aircraftComboBox.getItems().addAll(aircraftOperator.getInformationFromAirlineId(airlineChosen.getId()));
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
        arrivalGateComboBox.getItems().addAll(gateOperator.getInformationFromAirportId(arrivalAirportChosen.getId()));
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
        departureGateComboBox.getItems().addAll(gateOperator.getInformationFromAirportId(departureAirportChosen.getId()));
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

            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            int rowsAffected = flightOperator.updateById(flightChoiceComboBox.getValue().getId(), flight);

            if (rowsAffected == 0) {
                // Flight not edited. Display error message.
                Util.setMessageLabel("Flight not edited. Some error occurred.", Color.RED, messageLabel); //TODO what kind of errors can happen here?
            } else {
                // Airline inserted. Clear each component and display success message.
                clearComponents();
                Util.setMessageLabel("Flight edited.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
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

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     * The name of this method is the 'onAction' FXML value for the backButton.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void backButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }
}
