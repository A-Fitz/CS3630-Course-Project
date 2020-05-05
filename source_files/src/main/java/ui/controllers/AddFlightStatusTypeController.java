package ui.controllers;

import database.operators.FlightStatusTypeOperator;
import database.tables.base.FlightStatusType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the "Add Flight Status Type" screen. Will allow for inserting a flight status type into the database given valid input.
 */
public class AddFlightStatusTypeController implements Initializable {
    private FlightStatusTypeOperator flightStatusTypeOperator = FlightStatusTypeOperator.getInstance();
    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Label messageLabel;
    @FXML private TextField flightStatusTypeTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
    }

    /**
     * Called when the "Add Flight Status Type" button is clicked. Handles verifying user input so that a new flight
     * status type can be inserted into the database. Shows success and error messages.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addFlightStatusTypeButtonClicked(ActionEvent actionEvent) {
        if (flightStatusTypeTextField.getText() != null && !flightStatusTypeTextField.getText().isEmpty()) {
            FlightStatusType flightStatusType = new FlightStatusType();
            flightStatusType.setTitle(flightStatusTypeTextField.getText());

            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            int rowsAffected = flightStatusTypeOperator.insert(flightStatusType);

            if (rowsAffected == 0) {
                // Flight status type not inserted (probably due to unique constraint on title). Display error message.
                Util.setMessageLabel("Flight status type not added. The title is unique to a flight status type.", Color.RED, messageLabel);
            } else {
                // Flight status type inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Flight status type added.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
        } else {
            // All fields must not be null. Display error message.
            Util.setMessageLabel("Flight status type not added. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Clears all user-operable text fields on the screen.
     */
    private void clearAllTextFields() {
        flightStatusTypeTextField.clear();
    }

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void backButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }
}
