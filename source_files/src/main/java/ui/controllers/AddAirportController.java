package ui.controllers;

import database.operators.AirportOperator;
import database.tables.Airport;
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
 * Controls the "Add Airport" screen. Will allow for inserting an Airport into the database given valid input.
 */
public class AddAirportController extends ThreeColumnController {
    private AirportOperator airportOperator = AirportOperator.getInstance();

    @FXML private TextField nameTextField;
    @FXML private TextField iataCodeTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField countryTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
    }

    /**
     * Called when the "Add Airport" button is clicked. Handles verifying user input so that a new Airport can be inserted
     * into the database. Shows success and error messages.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addAirportButtonClicked(ActionEvent actionEvent) {
        if (nameTextField.getText() != null && !nameTextField.getText().isEmpty()
                && iataCodeTextField.getText() != null && !iataCodeTextField.getText().isEmpty()
                && cityTextField.getText() != null && !cityTextField.getText().isEmpty()
                && countryTextField.getText() != null && !countryTextField.getText().isEmpty()) {
            // disable buttons until a success/failure is received
            disable();

            Airport airport = new Airport();
            airport.setName(nameTextField.getText());
            airport.setIata_code(iataCodeTextField.getText());
            airport.setCity(cityTextField.getText());
            airport.setCountry(countryTextField.getText());

            try {
                airportOperator.insert(airport);
            }

            catch (DataAccessException dae) {
                // Airport not inserted (probably due to unique constraint on the IATA code). Display error message.
                Util.setMessageLabel("Airport not added. The IATA code is unique to an airport.", Color.RED, messageLabel);
                enable();
                return;
            }

                // Airport inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Airport added.", Color.GREEN, messageLabel);

            enable();
        } else {
            // All fields must not be empty. Display error message.
            Util.setMessageLabel("Airport not added. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Clears all user-operable text fields on the screen.
     */
    private void clearAllTextFields() {
        nameTextField.clear();
        iataCodeTextField.clear();
        cityTextField.clear();
        countryTextField.clear();
    }


}
