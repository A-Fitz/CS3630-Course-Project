package ui.controllers;

import database.operators.*;
import database.tables.base.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.Util;
import ui.converters.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {
    private AirportOperator airportOperator = AirportOperator.getInstance();
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();
    private AirportEmployeeOperator airportEmployeeOperator = AirportEmployeeOperator.getInstance();
    private AirlineEmployeeOperator airlineEmployeeOperator = AirlineEmployeeOperator.getInstance();
    private AirportJobTypeOperator airportJobTypeOperator = AirportJobTypeOperator.getInstance();
    private AirlineJobTypeOperator airlineJobTypeOperator = AirlineJobTypeOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private ComboBox<String> regionComboBox;
    @FXML private ComboBox workPlaceComboBox;
    @FXML private ComboBox jobTypeComboBox;
    @FXML private TextField firstNameTextField;
    @FXML private TextField middleNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;
    @FXML private DatePicker birthDateDatePicker;
    @FXML private Label messageLabel;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();
        });
    }

    public void addEmployeeButtonClicked()
    {
        if(regionComboBox.getValue()!=null &&
                workPlaceComboBox.getValue()!= null &&
                jobTypeComboBox.getValue()!= null &&
                firstNameTextField.getText()!= null &&
                middleNameTextField.getText()!= null &&
                lastNameTextField.getText()!= null &&
                emailTextField.getText()!= null &&
                addressTextField.getText()!= null &&
                phoneTextField.getText()!= null &&
                birthDateDatePicker.getValue()!= null)
        {
            if (regionComboBox.getValue().equals("Airport"))
            {
                setAirportEmployee();
            }
            else if (regionComboBox.getValue().equals("Airline"))
            {
                setAirlineEmployee();
            }
        }
        else
        {
            Util.setMessageLabel("Error. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    private void setAirportEmployee() {

        AirportEmployee airportEmployee = new AirportEmployee();


        airportEmployee.setAirport_id(((Airport) workPlaceComboBox.getValue()).getId());
        airportEmployee.setJob_id(((AirportJobType) jobTypeComboBox.getValue()).getId());
        airportEmployee.setFirst_name(firstNameTextField.getText());
        airportEmployee.setMiddle_name(middleNameTextField.getText());
        airportEmployee.setLast_name(lastNameTextField.getText());
        airportEmployee.setEmail(emailTextField.getText());
        airportEmployee.setAddress(addressTextField.getText());
        airportEmployee.setPhone(phoneTextField.getText());
        airportEmployee.setBirth_date(Date.valueOf(birthDateDatePicker.getValue()));
        mainGridPane.setDisable(true);
        messageLabel.setText("Request in progress...");
        int rowsAffected = airportEmployeeOperator.insert(airportEmployee);

        if (rowsAffected == 0)
        {
            Util.setMessageLabel("Airport Employee not added.", Color.RED, messageLabel);
        }
        else {
            clearAllFields();
            Util.setMessageLabel("Airport Employee added.", Color.GREEN, messageLabel);
        }
        mainGridPane.setDisable(false);
}

    private void setAirlineEmployee() {
        AirlineEmployee airlineEmployee = new AirlineEmployee();
        airlineEmployee.setAirline_id(((Airline) workPlaceComboBox.getValue()).getId());
        airlineEmployee.setJob_id(((AirlineJobType) jobTypeComboBox.getValue()).getId());
        airlineEmployee.setFirst_name(firstNameTextField.getText());
        airlineEmployee.setMiddle_name(middleNameTextField.getText());
        airlineEmployee.setLast_name(lastNameTextField.getText());
        airlineEmployee.setEmail(emailTextField.getText());
        airlineEmployee.setAddress(addressTextField.getText());
        airlineEmployee.setPhone(phoneTextField.getText());
        airlineEmployee.setBirth_date(Date.valueOf(birthDateDatePicker.getValue()));

        mainGridPane.setDisable(true);
        messageLabel.setText("Request in progress...");

        int rowsAffected = airlineEmployeeOperator.insert(airlineEmployee);

        if (rowsAffected == 0) {
            Util.setMessageLabel("Airline Employee not added.", Color.RED, messageLabel);
        } else {
            clearAllFields();
            Util.setMessageLabel("Airline Employee added.", Color.GREEN, messageLabel);
        }
        mainGridPane.setDisable(false);
    }


    /**
     * Use this event to limit the choices for the workPlaceComboBox and jobTypeComboBox.
     * @param actionEvent Event representing the action of the ComboBox item being clicked, holds extra information.
     */
    public void regionChosen(ActionEvent actionEvent) {
        workPlaceComboBox.getItems().clear();

        if(regionComboBox.getValue().equals("Airport"))
        {
            workPlaceComboBox.getItems().addAll(airportOperator.selectAll());
            workPlaceComboBox.setConverter(new AirportStringConverter());
            jobTypeComboBox.getItems().addAll(airportJobTypeOperator.selectAll());
            jobTypeComboBox.setConverter(new AirportJobTypeStringConverter());
        }
        else if(regionComboBox.getValue().equals("Airline"))
        {
            workPlaceComboBox.getItems().addAll(airlineOperator.selectAll());
            workPlaceComboBox.setConverter(new AirlineStringConverter());
            jobTypeComboBox.getItems().addAll(airlineJobTypeOperator.selectAll());
            jobTypeComboBox.setConverter(new AirlineJobTypeStringConverter());
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

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     */
    public void backButtonClicked() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }
}
