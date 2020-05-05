package ui.controllers;

import database.operators.*;
import database.tables.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.formatters.FloatTextFormatter;
import ui.Launcher;
import ui.Util;
import ui.converters.*;

import java.util.List;

public class AddBaggageController {
    private PassengerOnFlightOperator passengerOnFlightOperator = PassengerOnFlightOperator.getInstance();
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();
    private BaggageOperator baggageOperator = BaggageOperator.getInstance();
    private BaggageStatusTypeOperator baggageStatusTypeOperator = BaggageStatusTypeOperator.getInstance();

    @FXML
    private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Button addButton;
    @FXML private ComboBox<Flight> flightComboBox;
    @FXML private ComboBox<Passenger> passengerComboBox;
    @FXML private TextField weightTextField;
    @FXML private ComboBox<BaggageStatusType> baggageStatusComboBox;

    @FXML private Label messageLabel;

    @FXML
    public void initialize()
    {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());

        flightComboBox.setConverter(new FlightStringConverter());
        passengerComboBox.setConverter(new PassengerStringConverter());
        baggageStatusComboBox.setConverter(new BaggageStatusTypeStringConverter());

        weightTextField.setTextFormatter(new FloatTextFormatter());

        flightComboBox.getItems().addAll(flightOperator.selectAll());
        baggageStatusComboBox.getItems().addAll(baggageStatusTypeOperator.selectAll());
    }

    public void addBaggageButtonClicked(ActionEvent actionEvent) {
        if (flightComboBox.getValue() != null &&
                passengerComboBox.getValue() != null &&
                weightTextField.getText() != null && !weightTextField.getText().isEmpty() &&
                baggageStatusComboBox.getValue() != null)
        {
            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText("Request in progress...");

            PassengerOnFlight passengerOnFlight = passengerOnFlightOperator.selectByPassengerAndFlightId(passengerComboBox.getValue().getId(), flightComboBox.getValue().getId());

            Baggage baggage = new Baggage();
            baggage.setPassenger_on_flight_id(passengerOnFlight.getId());
            baggage.setWeight(Float.parseFloat(weightTextField.getText()));
            baggage.setBaggage_status_id(baggageStatusComboBox.getValue().getId());

            int rowsAffected = baggageOperator.insert(baggage);

            if (rowsAffected == 0) {
                // Baggage not inserted. Display error message.
                Util.setMessageLabel("Baggage not added.", Color.RED, messageLabel); //TODO: why?
            } else {
                // Baggage inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Baggage added.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
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
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     */
    public void backButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }

    /**
     * Called when the user chooses a Flight in the Flight ComboBox.
     * @param actionEvent holds extra event information
     */
    public void flightChosen(ActionEvent actionEvent) {
        if (flightComboBox.getValue() != null) {
            passengerComboBox.getItems().clear();
            passengerComboBox.setValue(null);
            List<PassengerOnFlight> passengerOnFlightObjects = passengerOnFlightOperator.selectByFlightId(flightComboBox.getValue().getId());
            for (PassengerOnFlight pof : passengerOnFlightObjects) {
                passengerComboBox.getItems().add(passengerOperator.selectById(pof.getPassenger_id()));
            }
        }
    }
}
