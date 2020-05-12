package ui.controllers;

import database.operators.FlightStatusTypeOperator;
import database.tables.FlightStatusType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.dao.DataAccessException;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the "Add Flight Status Type" screen. Will allow for inserting a flight status type into the database given valid input.
 */
public class AddFlightStatusTypeController extends ThreeColumnController {
    private FlightStatusTypeOperator flightStatusTypeOperator = FlightStatusTypeOperator.getInstance();

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
            // disable buttons until a success/failure is received
            disable();

            FlightStatusType flightStatusType = new FlightStatusType();
            flightStatusType.setTitle(flightStatusTypeTextField.getText());

            try {
                flightStatusTypeOperator.insert(flightStatusType);
            }

            catch (DataAccessException dae) {
                // Flight status type not inserted (probably due to unique constraint on title). Display error message.
                Util.setMessageLabel("Flight status type not added. The title is unique to a flight status type.", Color.RED, messageLabel);
                enable();
                return;
            }
                // Flight status type inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Flight status type added.", Color.GREEN, messageLabel);
            enable();
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


}
