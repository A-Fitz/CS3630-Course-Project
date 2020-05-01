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
import ui.Launcher;
import ui.Util;
import ui.converters.AirlineStringConverter;
import ui.converters.BaggageStatusTypeStringConverter;
import ui.converters.FlightStringConverter;
import ui.converters.PassengerOnFlightStringConverter;

import java.sql.Date;
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
    @FXML private ComboBox flightIdField;
    @FXML private ComboBox passengerIdField;
    @FXML private TextField weightTextField;
    @FXML private ComboBox baggageStatusField;

    @FXML private Label messageLabel;

    @FXML
    public void initialize()
    {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
        flightIdField.setConverter(new FlightStringConverter());
        baggageStatusField.setConverter(new BaggageStatusTypeStringConverter());
        flightIdField.getItems().addAll(flightOperator.selectAll());
        baggageStatusField.getItems().addAll(baggageStatusTypeOperator.selectAll());
    }

    public void addBaggageButtonClicked(ActionEvent actionEvent) {
        if (flightIdField.getValue()!= null &&
                passengerIdField.getValue() != null &&
                weightTextField.getText()!= null &&
                baggageStatusField.getValue()!= null ) {
            Baggage baggage = new Baggage();
            List<PassengerOnFlight> passengerOnFlightObjects = passengerOnFlightOperator.selectAll();
            PassengerOnFlight passengerOnFlight;
            Flight chosenFlight = (Flight) flightIdField.getValue();
            Passenger chosenPassenger = (Passenger) passengerIdField.getValue();
            for (PassengerOnFlight pof : passengerOnFlightObjects)
            {
                if (pof.getFlight_id() == chosenFlight.getId()
                    && pof.getPassenger_id() == chosenPassenger.getId())
                    baggage.setPassenger_on_flight_id(pof.getId());
            }
            baggage.setWeight(Float.valueOf(weightTextField.getText()));
            baggage.setBaggage_status_id(Integer.valueOf(((BaggageStatusType)baggageStatusField.getValue()).getId()));

            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText("Request in progress...");

            int rowsAffected = baggageOperator.insert(baggage);

            if (rowsAffected == 0) {
                // Baggage not inserted (probably due to unique constraints on abbreviation or name). Display error message.
                Util.setMessageLabel("Baggage not added.", Color.RED, messageLabel);
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
     * Clears all user-operable text fields on the screen.
     */
    private void clearAllTextFields() {
        flightIdField.setValue(null);
        passengerIdField.setValue(null);
        weightTextField.clear();
        baggageStatusField.setValue(null);
    }

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     *
     */
    public void backButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }

    public void flightChosen(ActionEvent actionEvent) {
        Flight flightChosen = (Flight) flightIdField.getValue();
        passengerIdField.getItems().clear();
        passengerIdField.setValue(null);
        List<PassengerOnFlight> passengerOnFlightObjects = passengerOnFlightOperator.selectAll();
        for (PassengerOnFlight pof : passengerOnFlightObjects)
        {
            if (pof.getFlight_id() == flightChosen.getId())
                passengerIdField.getItems().add(passengerOperator.selectById(pof.getPassenger_id()));
        }
    }
}
