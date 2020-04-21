package ui.controllers;

import database.operators.*;
import database.tables.*;
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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the "Edit Flight" screen. Will allow for editing a row of the flight table in the database given valid input.
 */
public class EditFlightController implements Initializable {
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
        Platform.runLater( () -> backButton.getScene().getRoot().requestFocus() );
    }

    /**
     * Called when the flight which is to be edited is chosen. Should fill the values of the TextFields and ComboBoxes
     * with what the flight currently has. Limit the gate choices to only what the departure and arrival airports have.
     * Limit the aircraft choices to only what the airline has.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void flightChosen(ActionEvent actionEvent) {

    }

    /**
     * Called when the user chooses a new option in the "Airline" ComboBox. This should change the options
     * in the "Aircraft" ComboBox so that only correct aircrafts can be chosen.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void airlineChosen(ActionEvent actionEvent) {

    }

    /**
     * Called when the user chooses a new option in the "Arrival Airport" ComboBox. This should change the options
     * in the "Arrival Gate" ComboBox so that only correct gates can be chosen.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void arrivalAirportChosen(ActionEvent actionEvent) {

    }


    /**
     * Called when the user chooses a new option in the "Departure Airport" ComboBox. This should change the options
     * in the "Departure Gate" ComboBox so that only correct gates can be chosen.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void departureAirportChosen(ActionEvent actionEvent) {

    }

    /**
     * Called when the "Edit Flight" button is clicked. Handles verifying user input so that a row in the flight table
     * can be edited. Shows success and error messages.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void editFlightButtonClicked(ActionEvent actionEvent) {

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
