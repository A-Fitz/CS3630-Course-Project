package ui.controllers;

import database.operators.PassengerOperator;
import database.tables.Passenger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ui.Util;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddPassengerController extends ThreeColumnController {
    private PassengerOperator operator = PassengerOperator.getInstance();

    @FXML private Button addButton;
    @FXML private TextField passportNumberTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField middleNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;
    @FXML private DatePicker birthDateDatePicker;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
                backButton.getScene().getRoot().requestFocus());
    }

    public void addPassengerButtonClicked() {
        if (firstNameTextField.getText() != null &&
                middleNameTextField.getText() != null &&
                lastNameTextField.getText() != null &&
                emailTextField.getText() != null &&
                addressTextField.getText() != null &&
                phoneTextField.getText() != null &&
                birthDateDatePicker.getValue() != null) {
            // disable buttons until a success/failure is received
            disable();

            Passenger passenger = new Passenger();

            // passport number is allowed to be null
            if (passportNumberTextField.getText() != null && !passportNumberTextField.getText().isEmpty())
                passenger.setPassport_number(passportNumberTextField.getText());

            passenger.setFirst_name(firstNameTextField.getText());
            passenger.setMiddle_name(middleNameTextField.getText());
            passenger.setLast_name(lastNameTextField.getText());
            passenger.setEmail(emailTextField.getText());
            passenger.setAddress(addressTextField.getText());
            passenger.setPhone(phoneTextField.getText());
            passenger.setBirth_date(Date.valueOf(birthDateDatePicker.getValue()));



            int rowsAffected = operator.insert(passenger);

            if (rowsAffected == 0) {
                // Passenger not inserted (probably due to unique constraints on abbreviation or name). Display error message.
                Util.setMessageLabel("Passenger not added.", Color.RED, messageLabel);
            } else {
                // Passenger inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Passenger added.", Color.GREEN, messageLabel);
            }
            enable();
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
        birthDateDatePicker.setValue(null);
    }
}
