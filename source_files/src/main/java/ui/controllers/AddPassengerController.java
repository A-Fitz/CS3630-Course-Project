package ui.controllers;

import database.operators.PassengerOperator;
import database.tables.Passenger;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class AddPassengerController {
    private PassengerOperator operator = PassengerOperator.getInstance();

    @FXML
    private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Button addButton;
    @FXML private TextField passportNumberTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField middleNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;
    @FXML private DatePicker birthDayField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize()
    {
        backButton.setOnAction(e -> backButtonClicked());
        addButton.setOnAction(e -> addPassengerButtonClicked());
    }

    public void addPassengerButtonClicked() {
        if (passportNumberTextField.getText()!= null ||
                firstNameTextField.getText()!= null ||
                middleNameTextField.getText()!= null ||
                lastNameTextField.getText()!= null  ||
                lastNameTextField.getText()!= null  ||
                lastNameTextField.getText()!= null  ||
                lastNameTextField.getText()!= null  ||
                lastNameTextField.getText()!= null) {
            Passenger passenger = new Passenger();
            passenger.setPassport_number(passportNumberTextField.getText());
            passenger.setFirst_name(firstNameTextField.getText());
            passenger.setMiddle_name(middleNameTextField.getText());
            passenger.setLast_name(lastNameTextField.getText());
            passenger.setEmail(emailTextField.getText());
            passenger.setAddress(addressTextField.getText());
            passenger.setPhone(Integer.valueOf(phoneTextField.getText()));
            passenger.setBirth_date(Date.valueOf(birthDayField.getValue()));




            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText("Request in progress...");

            int rowsAffected = operator.insert(passenger);

            if (rowsAffected == 0) {
                // Passenger not inserted (probably due to unique constraints on abbreviation or name). Display error message.
                Util.setMessageLabel("Passenger not added.", Color.RED, messageLabel);
            } else {
                // Passenger inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Passenger added.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
        } else {
            // All fields must not be null. Display error message.
            Util.setMessageLabel("Passenger not added. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Clears all user-operable text fields on the screen.
     */
    private void clearAllTextFields() {
        passportNumberTextField.clear();
        firstNameTextField.clear();
        middleNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        addressTextField.clear();
        phoneTextField.clear();
        birthDayField.setValue(null);
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
