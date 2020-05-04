package ui.controllers;

import database.operators.AirlineOperator;
import database.operators.BaggageStatusTypeOperator;
import database.tables.Airline;
import database.tables.BaggageStatusType;
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
 * Controls the "Add Baggage Status Type" screen. Will allow for inserting a baggage status type into the database given valid input.
 */
public class AddBaggageStatusTypeController implements Initializable {
    private BaggageStatusTypeOperator baggageStatusTypeOperator = BaggageStatusTypeOperator.getInstance();
    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Label messageLabel;
    @FXML private TextField baggageStatusTypeTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
    }

    /**
     * Called when the "Add Baggage Status Type" button is clicked. Handles verifying user input so that a new baggage
     * status type can be inserted into the database. Shows success and error messages.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addBaggageStatusTypeButtonClicked(ActionEvent actionEvent) {
        if (baggageStatusTypeTextField.getText() != null && !baggageStatusTypeTextField.getText().isEmpty()) {
            BaggageStatusType baggageStatusType = new BaggageStatusType();
            baggageStatusType.setTitle(baggageStatusTypeTextField.getText());

            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            int rowsAffected = baggageStatusTypeOperator.insert(baggageStatusType);

            if (rowsAffected == 0) {
                // Baggage status type not inserted (probably due to unique constraint on title). Display error message.
                Util.setMessageLabel("Baggage status type not added. The title is unique to a baggage status type.", Color.RED, messageLabel);
            } else {
                // Baggage status type inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Baggage status type added.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
        } else {
            // All fields must not be null. Display error message.
            Util.setMessageLabel("Baggage status type not added. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Clears all user-operable text fields on the screen.
     */
    private void clearAllTextFields() {
        baggageStatusTypeTextField.clear();
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
