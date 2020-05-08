package ui.controllers;

import database.operators.AirlineEmployeeOnFlightOperator;
import database.operators.AirlineEmployeeOperator;
import database.operators.FlightOperator;
import database.tables.AirlineEmployee;
import database.tables.AirlineEmployeeOnFlight;
import database.tables.Flight;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class representing an example controller for others to use. Is related to the resources/ExampleScreen.fxml FXML file for the UI.
 */
public class AddAirlineEmployeeToFlightController extends ThreeColumnController {
    private AirlineEmployeeOperator airlineEmployeeOperator = AirlineEmployeeOperator.getInstance();
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private AirlineEmployeeOnFlightOperator airlineEmployeeOnFlightOperator = AirlineEmployeeOnFlightOperator.getInstance();


    @FXML private ComboBox<AirlineEmployee> employeeComboBox;
    @FXML private ComboBox<Flight> flightComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus()); // Do this to stop text fields from getting auto focus (annoying).

        employeeComboBox.getItems().addAll(airlineEmployeeOperator.selectAll());
        flightComboBox.getItems().addAll(flightOperator.selectAll());
    }

    /**
     * Called when the "do something" button is clicked. Use a method like this to check if their input is valid and
     * then try the operation. If it works display a success message, if not display an error message.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addAirlineEmployeeToFlightButtonClicked(ActionEvent actionEvent) {

        if (employeeComboBox.getValue() != null &&
                flightComboBox.getValue() != null) {

            disable();

            AirlineEmployeeOnFlight airlineEmployeeOnFlight = new AirlineEmployeeOnFlight();
            airlineEmployeeOnFlight.setAirline_employee_id(employeeComboBox.getValue().getId());
            airlineEmployeeOnFlight.setFlight_id(flightComboBox.getValue().getId());

            int rowsAffected = airlineEmployeeOnFlightOperator.insert(airlineEmployeeOnFlight);

            if (rowsAffected == 0)
                Util.setMessageLabel("Something went wrong.", Color.RED, messageLabel); //TODO : what went wrong?
            else {
                clearAllFields();
                Util.setMessageLabel("Airline employee was added to flight.", Color.GREEN, messageLabel);
            }

        } else {
            Util.setMessageLabel("Airline employee was not added to flight. Please fill all fields.", Color.RED, messageLabel);
        }
        enable();
    }

    /**
     * Clears all user-operable fields on the screen.
     */
    private void clearAllFields() {
        employeeComboBox.setValue(null);
        flightComboBox.setValue(null);
    }
}
