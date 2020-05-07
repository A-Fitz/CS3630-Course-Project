package ui.controllers;

import database.operators.AirportOperator;
import database.tables.Airport;
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
 * Controls the "Add Airport" screen. Will allow for inserting an Airport into the database given valid input.
 */
public class AddAirportController implements Initializable {
    private AirportOperator airportOperator = AirportOperator.getInstance();
    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Label messageLabel;
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
            Airport airport = new Airport();
            airport.setName(nameTextField.getText());
            airport.setIata_code(iataCodeTextField.getText());
            airport.setCity(cityTextField.getText());
            airport.setCountry(countryTextField.getText());

            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            int rowsAffected = airportOperator.insert(airport);

            if (rowsAffected == 0) {
                // Airport not inserted (probably due to unique constraint on the IATA code). Display error message.
                Util.setMessageLabel("Airport not added. The IATA code is unique to an airport.", Color.RED, messageLabel);
            } else {
                // Airport inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Airport added.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
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
