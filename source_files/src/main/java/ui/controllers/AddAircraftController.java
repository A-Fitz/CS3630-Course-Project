package ui.controllers;

import database.operators.AircraftOperator;
import database.operators.AirlineOperator;
import database.tables.Aircraft;
import database.tables.Airline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.dao.DataAccessException;
import ui.Util;
import ui.exceptions.IncorrectInputException;
import ui.formatters.IntegerTextFormatter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Add Aircraft view.
 */
public class AddAircraftController extends ThreeColumnController {
    private AircraftOperator aircraftOperator = AircraftOperator.getInstance();
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();


    @FXML
    private ComboBox<Airline> airlineChoiceComboBox;
    @FXML
    private TextField serialNumberTextField;
    @FXML
    private TextField makeTextField;
    @FXML
    private TextField modelTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField capacityTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
                backButton.getScene().getRoot().requestFocus());

        airlineChoiceComboBox.getItems().addAll(airlineOperator.selectAll());

        yearTextField.setTextFormatter(new IntegerTextFormatter());
        capacityTextField.setTextFormatter(new IntegerTextFormatter());
    }

    /**
     * Called when the "Add Aircraft" button is clicked. Handles verifying user input so that a new Aircraft can be inserted
     * into the database. Shows success and error messages.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addAircraftButtonClicked(ActionEvent actionEvent) {
        if (airlineChoiceComboBox.getValue() != null
                && serialNumberTextField.getText() != null && !serialNumberTextField.getText().isEmpty()
                && makeTextField.getText() != null && !makeTextField.getText().isEmpty()
                && modelTextField.getText() != null && !modelTextField.getText().isEmpty()
                && yearTextField.getText() != null && !yearTextField.getText().isEmpty()
                && capacityTextField.getText() != null && !capacityTextField.getText().isEmpty()) {
            try {
                if (yearTextField.getText().length() != 4) // Check if the year is length 4
                    throw new IncorrectInputException("The year field must be four digits in length.");

                // disable buttons until a success/failure is received
                disable();

                Aircraft aircraft = new Aircraft();
                aircraft.setAirline_id(airlineChoiceComboBox.getValue().getId());
                aircraft.setSerial_number(serialNumberTextField.getText());
                aircraft.setMake(makeTextField.getText());
                aircraft.setModel(modelTextField.getText());
                aircraft.setYear(Integer.parseInt(yearTextField.getText()));
                aircraft.setCapacity(Integer.parseInt((capacityTextField.getText())));

                try {
                    aircraftOperator.insert(aircraft);
                } catch (DataAccessException dae) {
                    Util.setMessageLabel("Aircraft not added. The serial number is unique to an aircraft.", Color.RED, messageLabel);
                    enable();
                    return;
                }

                clearComponents();
                Util.setMessageLabel("Aircraft added.", Color.GREEN, messageLabel);

                enable();
            } catch (IncorrectInputException iie) {
                Util.setMessageLabel(iie.getMessage(), Color.RED, messageLabel);
            }
        } else
            Util.setMessageLabel("Aircraft not added. Please fill the required fields.", Color.RED, messageLabel);
    }

    private void clearComponents() {
        airlineChoiceComboBox.setValue(null);
        serialNumberTextField.clear();
        makeTextField.clear();
        modelTextField.clear();
        yearTextField.clear();
        capacityTextField.clear();
    }
}
