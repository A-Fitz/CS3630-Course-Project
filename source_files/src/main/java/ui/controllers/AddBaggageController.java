package ui.controllers;

import database.operators.BaggageOperator;
import database.tables.Baggage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.Util;

import java.sql.Date;

public class AddBaggageController {
    private BaggageOperator operator = BaggageOperator.getInstance();

    @FXML
    private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Button addButton;
    @FXML private TextField passportOnFlightIdTextField;
    @FXML private TextField weightTextField;
    @FXML private TextField baggageStatusTextField;

    @FXML private Label messageLabel;

    @FXML
    public void initialize()
    {
        backButton.setOnAction(e -> backButtonClicked());
        addButton.setOnAction(e -> addBaggageButtonClicked());
    }

    public void addBaggageButtonClicked() {
        if (passportOnFlightIdTextField.getText()!= null ||
                weightTextField.getText()!= null ||
                baggageStatusTextField.getText()!= null ) {
            Baggage baggage = new Baggage();
            baggage.setPassenger_on_flight_id(Integer.valueOf(passportOnFlightIdTextField.getText()));
            baggage.setWeight(Float.valueOf(weightTextField.getText()));
            baggage.setBaggage_status_id(Integer.valueOf(baggageStatusTextField.getText()));

            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText("Request in progress...");

            int rowsAffected = operator.insert(baggage);

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
        passportOnFlightIdTextField.clear();
        weightTextField.clear();
        baggageStatusTextField.clear();
    }

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     *
     */
    public void backButtonClicked() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }
}
