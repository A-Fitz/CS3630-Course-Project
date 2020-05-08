package ui.controllers;

import database.tables.DatabaseObject;
import database.operators.*;
import database.tables.AirlineEmployee;
import database.tables.AirportEmployee;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ui.Util;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddEmployeeController extends ThreeColumnController {
    private AirportOperator airportOperator = AirportOperator.getInstance();
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();
    private AirportEmployeeOperator airportEmployeeOperator = AirportEmployeeOperator.getInstance();
    private AirlineEmployeeOperator airlineEmployeeOperator = AirlineEmployeeOperator.getInstance();
    private AirportJobTypeOperator airportJobTypeOperator = AirportJobTypeOperator.getInstance();
    private AirlineJobTypeOperator airlineJobTypeOperator = AirlineJobTypeOperator.getInstance();

    @FXML private ComboBox<String> regionComboBox;
    @FXML private ComboBox<DatabaseObject> workPlaceComboBox;
    @FXML private ComboBox<DatabaseObject> jobTypeComboBox;
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

    public void addEmployeeButtonClicked() {
        if (regionComboBox.getValue() != null &&
                workPlaceComboBox.getValue() != null &&
                jobTypeComboBox.getValue() != null &&
                firstNameTextField.getText() != null && !firstNameTextField.getText().isEmpty() &&
                middleNameTextField.getText() != null && !middleNameTextField.getText().isEmpty() &&
                lastNameTextField.getText() != null && !lastNameTextField.getText().isEmpty() &&
                emailTextField.getText() != null && !emailTextField.getText().isEmpty() &&
                addressTextField.getText() != null && !addressTextField.getText().isEmpty() &&
                phoneTextField.getText() != null && !phoneTextField.getText().isEmpty() &&
                birthDateDatePicker.getValue() != null) {
            disable();
            if (regionComboBox.getValue().equals("Airport")) {
                setAirportEmployee();
            } else if (regionComboBox.getValue().equals("Airline")) {
                setAirlineEmployee();
            }
            enable();
        } else {
            Util.setMessageLabel("Error. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    private void setAirportEmployee() {
        AirportEmployee airportEmployee = new AirportEmployee();
        airportEmployee.setAirport_id(workPlaceComboBox.getValue().getId());
        airportEmployee.setJob_id(jobTypeComboBox.getValue().getId());
        airportEmployee.setFirst_name(firstNameTextField.getText());
        airportEmployee.setMiddle_name(middleNameTextField.getText());
        airportEmployee.setLast_name(lastNameTextField.getText());
        airportEmployee.setEmail(emailTextField.getText());
        airportEmployee.setAddress(addressTextField.getText());
        airportEmployee.setPhone(phoneTextField.getText());
        airportEmployee.setBirth_date(Date.valueOf(birthDateDatePicker.getValue()));

        int rowsAffected = airportEmployeeOperator.insert(airportEmployee);

        if (rowsAffected == 0) {
            Util.setMessageLabel("Airport Employee not added.", Color.RED, messageLabel);
        } else {
            clearAllFields();
            Util.setMessageLabel("Airport Employee added.", Color.GREEN, messageLabel);
        }
    }

    private void setAirlineEmployee() {
        AirlineEmployee airlineEmployee = new AirlineEmployee();
        airlineEmployee.setAirline_id(workPlaceComboBox.getValue().getId());
        airlineEmployee.setJob_id(jobTypeComboBox.getValue().getId());
        airlineEmployee.setFirst_name(firstNameTextField.getText());
        airlineEmployee.setMiddle_name(middleNameTextField.getText());
        airlineEmployee.setLast_name(lastNameTextField.getText());
        airlineEmployee.setEmail(emailTextField.getText());
        airlineEmployee.setAddress(addressTextField.getText());
        airlineEmployee.setPhone(phoneTextField.getText());
        airlineEmployee.setBirth_date(Date.valueOf(birthDateDatePicker.getValue()));

        int rowsAffected = airlineEmployeeOperator.insert(airlineEmployee);

        if (rowsAffected == 0) {
            Util.setMessageLabel("Airline Employee not added.", Color.RED, messageLabel);
        } else {
            clearAllFields();
            Util.setMessageLabel("Airline Employee added.", Color.GREEN, messageLabel);
        }
    }

    /**
     * Use this event to limit the choices for the workPlaceComboBox and jobTypeComboBox.
     *
     * @param actionEvent Event representing the action of the ComboBox item being clicked, holds extra information.
     */
    public void regionChosen(ActionEvent actionEvent) {
        workPlaceComboBox.getItems().clear();

        if (regionComboBox.getValue().equals("Airport")) {
            workPlaceComboBox.getItems().addAll(airportOperator.selectAll());
            jobTypeComboBox.getItems().addAll(airportJobTypeOperator.selectAll());
        } else if (regionComboBox.getValue().equals("Airline")) {
            workPlaceComboBox.getItems().addAll(airlineOperator.selectAll());
            jobTypeComboBox.getItems().addAll(airlineJobTypeOperator.selectAll());
        }
    }

    /**
     * Clears all user-operable fields on the screen.
     */
    private void clearAllFields() {
        workPlaceComboBox.getItems().clear();
        jobTypeComboBox.getItems().clear();
        firstNameTextField.clear();
        middleNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        addressTextField.clear();
        phoneTextField.clear();
        birthDateDatePicker.setValue(null);
    }
}
