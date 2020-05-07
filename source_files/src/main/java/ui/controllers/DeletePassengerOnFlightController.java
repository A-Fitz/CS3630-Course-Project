package ui.controllers;

import database.operators.FlightOperator;
import database.operators.PassengerOnFlightOperator;
import database.operators.PassengerOperator;
import database.tables.Flight;
import database.tables.Passenger;
import database.tables.PassengerOnFlight;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;
import ui.Util;

public class DeletePassengerOnFlightController {
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private PassengerOnFlightOperator passengerOnFlightOperator = PassengerOnFlightOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private ComboBox<Flight> flightComboBox;
    @FXML private ComboBox<Passenger> passengerComboBox;
    @FXML private Label messageLabel;

    public void initialize() {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();
        });
        flightComboBox.getItems().addAll(flightOperator.selectAll());
    }

    public void deletePassengerOnFlightButtonClicked() {
        if (flightComboBox.getValue() != null && passengerComboBox.getValue() != null) {
            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            int rowsAffected = passengerOnFlightOperator.deleteById(
                    passengerOnFlightOperator.selectByPassengerAndFlightId(
                            passengerComboBox.getValue().getId(), flightComboBox.getValue().getId()
                    ).getId());

            if (rowsAffected == 0) {
                Util.setMessageLabel("Passenger on flight not deleted.", Color.RED, messageLabel); // TODO for what reason could this happen?
            } else {
                clearAllFields();
                Util.setMessageLabel("Passenger on flight deleted.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
        } else {
            Util.setMessageLabel("Error. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Called when the user chooses a Flight in the Flight ComboBox. Fills passenger combo box.
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

    /**
     * Clears all user-operable fields on the screen.
     */
    private void clearAllFields() {
        flightComboBox.setValue(null);
        passengerComboBox.setValue(null);
        passengerComboBox.getItems().clear();
    }

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     */
    public void backButtonClicked() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }
}
