package ui.controllers;

import database.operators.AirlineOperator;
import database.tables.Airline;
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
 * Controls the "Add Airline" screen. Will allow for inserting an Airline into the database given valid input.
 */
public class AddAirlineController extends ThreeColumnController {
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();

    @FXML private TextField abbreviationTextField;
    @FXML private TextField nameTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
    }

    /**
     * Called when the "Add Airline" button is clicked. Handles verifying user input so that a new Airline can be inserted
     * into the database. Shows success and error messages.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addAirlineButtonClicked(ActionEvent actionEvent) {
        if (abbreviationTextField.getText() != null && !abbreviationTextField.getText().isEmpty()
                && nameTextField.getText() != null && !nameTextField.getText().isEmpty()) {
            Airline airline = new Airline();
            airline.setAbbreviation(abbreviationTextField.getText());
            airline.setName(nameTextField.getText());

            // disable buttons until a success/failure is received
            disable();

            try {
                airlineOperator.insert(airline);
            }

            catch (DataAccessException dae) {
                // Airline not inserted (probably due to unique constraints on abbreviation or name). Display error message.
                Util.setMessageLabel("Airline not added. Both the abbreviation and name fields are unique to an airline.", Color.RED, messageLabel);
                enable();
                return;
            }

                clearAllTextFields();
                Util.setMessageLabel("Airline added.", Color.GREEN, messageLabel);

            enable();
        } else {
            // All fields must not be null. Display error message.
            Util.setMessageLabel("Airline not added. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Clears all user-operable text fields on the screen.
     */
    private void clearAllTextFields() {
        abbreviationTextField.clear();
        nameTextField.clear();
    }
}
