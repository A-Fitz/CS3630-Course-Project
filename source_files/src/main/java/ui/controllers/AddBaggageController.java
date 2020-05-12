package ui.controllers;

import database.operators.*;
import database.tables.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.dao.DataAccessException;
import ui.Util;
import ui.formatters.FloatTextFormatter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBaggageController extends ThreeColumnController {
    private PassengerOnFlightOperator passengerOnFlightOperator = PassengerOnFlightOperator.getInstance();
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();
    private BaggageOperator baggageOperator = BaggageOperator.getInstance();
    private BaggageStatusTypeOperator baggageStatusTypeOperator = BaggageStatusTypeOperator.getInstance();

    @FXML private Button addButton;
    @FXML private ComboBox<Flight> flightComboBox;
    @FXML private ComboBox<Passenger> passengerComboBox;
    @FXML private TextField weightTextField;
    @FXML private ComboBox<BaggageStatusType> baggageStatusComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());

        weightTextField.setTextFormatter(new FloatTextFormatter());

        flightComboBox.getItems().addAll(flightOperator.selectAll());
        baggageStatusComboBox.getItems().addAll(baggageStatusTypeOperator.selectAll());
    }

    public void addBaggageButtonClicked(ActionEvent actionEvent) {
        if (flightComboBox.getValue() != null &&
                passengerComboBox.getValue() != null &&
                weightTextField.getText() != null && !weightTextField.getText().isEmpty() &&
                baggageStatusComboBox.getValue() != null) {
            // disable buttons until a success/failure is received
            disable();

            PassengerOnFlight passengerOnFlight = passengerOnFlightOperator.selectByPassengerAndFlightId(passengerComboBox.getValue().getId(), flightComboBox.getValue().getId());

            Baggage baggage = new Baggage();
            baggage.setPassenger_on_flight_id(passengerOnFlight.getId());
            baggage.setWeight(Float.parseFloat(weightTextField.getText()));
            baggage.setBaggage_status_id(baggageStatusComboBox.getValue().getId());

            try {
                baggageOperator.insert(baggage);
            }

            catch (DataAccessException dae) {
                // Baggage not inserted. Display error message.
                Util.setMessageLabel("Baggage not added.", Color.RED, messageLabel); //TODO: why?
                return;
            }
                // Baggage inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Baggage added.", Color.GREEN, messageLabel);
            enable();
        } else {
            // All fields must not be null. Display error message.
            Util.setMessageLabel("Baggage not added. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Clears all user-operable text fields on the screen. Also get rid of selected values for the ComboBoxes.
     * Do not clear the items for the Flight ComboBox as those don't need to be regenerated.
     */
    private void clearAllTextFields() {
        flightComboBox.setValue(null);
        passengerComboBox.setValue(null);
        passengerComboBox.getItems().clear();
        weightTextField.clear();
        baggageStatusComboBox.setValue(null);
    }

    /**
     * Called when the user chooses a Flight in the Flight ComboBox.
     *
     * @param actionEvent holds extra event information
     */
    public void flightChosen(ActionEvent actionEvent) {
        if (flightComboBox.getValue() != null) {
            passengerComboBox.getItems().clear();
            passengerComboBox.setValue(null);
            passengerComboBox.getItems().addAll(passengerOperator.selectManyByFlightId(flightComboBox.getValue().getId()));
        }
    }
}
