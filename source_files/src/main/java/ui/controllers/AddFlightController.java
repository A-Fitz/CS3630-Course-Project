package ui.controllers;

import database.operators.*;
import database.tables.base.*;
import database.tables.information.AircraftInformation;
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

public class AddFlightController implements Initializable {
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();
    private AirportOperator airportOperator = AirportOperator.getInstance();
    private GateOperator gateOperator = GateOperator.getInstance();
    private TerminalOperator terminalOperator = TerminalOperator.getInstance();
    private AircraftOperator aircraftOperator = AircraftOperator.getInstance();
    private FlightStatusTypeOperator flightStatusTypeOperator = FlightStatusTypeOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Label messageLabel;
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
        aircraftComboBox.getItems().addAll(aircraftOperator.getInformationFromAirlineId(airlineChosen.getId()));
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

            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            int rowsAffected = flightOperator.insert(flight);

            if (rowsAffected == 0) {
                // Flight not edited. Display error message.
                Util.setMessageLabel("Flight not added. Some error occurred.", Color.RED, messageLabel); //TODO what kind of errors can happen here?
            } else {
                // Flight inserted. Clear each component and display success message.
                clearComponents();
                Util.setMessageLabel("Flight added.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
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
