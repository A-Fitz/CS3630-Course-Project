package ui.controllers;

import database.operators.AirlineOperator;
import database.tables.Airline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.dao.DuplicateKeyException;
import ui.Launcher;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the "Add Airline" screen. Will allow for inserting an Airline into the database given valid input.
 */
public class AddAirlineController implements Initializable {
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();
    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private TextField abbreviationTextField;
    @FXML private TextField nameTextField;
    @FXML private Label messageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater( () -> backButton.getScene().getRoot().requestFocus() );
    }

    /**
     * Called when the "Add Airline" button is clicked. Handles verifying user input so that a new Airline can be inserted
     * into the database. Shows success and error messages.
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addAirlineButtonClicked(ActionEvent actionEvent) {
        if (abbreviationTextField.getText() != null && !abbreviationTextField.getText().isEmpty()
                && nameTextField.getText() != null && !nameTextField.getText().isEmpty()) {
            Airline airline = new Airline();
            airline.setAbbreviation(abbreviationTextField.getText());
            airline.setName(nameTextField.getText());

            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText("Request in progress...");

            int rowsAffected = airlineOperator.insert(airline);

            if (rowsAffected == 0) {
                // Airline not inserted (probably due to unique constraints on abbreviation or name). Display error message.
                Util.setMessageLabel("Airline not added. Both the abbreviation and name fields are unique to an airline.", Color.RED, 5, messageLabel);
            } else {
                // Airline inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Airline added.", Color.GREEN, 5, messageLabel);
            }
            mainGridPane.setDisable(false);
        } else {
            // All fields must not be null. Display error message.
            Util.setMessageLabel("Airline not added. Please fill the required fields.", Color.RED, 5, messageLabel);
        }
    }

    /**
     * Clears all user-operable text fields on the screen.
     */
    private void clearAllTextFields() {
        abbreviationTextField.clear();
        nameTextField.clear();
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
