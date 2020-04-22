package ui.controllers;

import database.operators.AircraftOperator;
import database.operators.AirlineOperator;
import database.tables.Aircraft;
import database.tables.Airline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;
import ui.Util;
import ui.converters.AirlineStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class representing an example controller for others to use. Is related to the resources/ExampleScreen.fxml FXML file for the UI.
 */
public class AddAircraftController implements Initializable {
    private AircraftOperator aircraftOperator = AircraftOperator.getInstance();
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();

    // Put your JavaFX components here. Buttons, Labels, TextFields, etc. The name of this variable will be the fx:id of the component in FXML.
    // Follow camelCase naming with the component type as the last phrase in the word (i.e. 'Button' is the last phrase).
    // Each controller should have the following components.
    @FXML
    private GridPane mainGridPane;
    @FXML
    private Button backButton;
    @FXML
    private Label messageLabel;

    // The following are example components.
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
        {
            backButton.getScene().getRoot().requestFocus();
            airlineChoiceComboBox.setConverter(new AirlineStringConverter());
        });

        // This is where your code for doing any programatic ui adjustments should go.
        airlineChoiceComboBox.getItems().addAll(airlineOperator.selectAll());
    }

    /**
     * Called when the "Add Aircraft" button is clicked. Handles verifying user input so that a new Aircraft can be inserted
     * into the database. Shows success and error messages.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addAircraftButtonClicked(ActionEvent actionEvent) {
        try { //The try block is used to check for non-integer input for year and capacity
            if (airlineChoiceComboBox.getValue() != null
                    && serialNumberTextField.getText() != null && !serialNumberTextField.getText().isEmpty()
                    && makeTextField.getText() != null && !makeTextField.getText().isEmpty()
                    && modelTextField.getText() != null && !modelTextField.getText().isEmpty()
                    && yearTextField.getText() != null && !yearTextField.getText().isEmpty()
                    && capacityTextField.getText() != null && !capacityTextField.getText().isEmpty()) {

                int year = Integer.parseInt(yearTextField.getText()); //Handled by catch block
                int capacity = Integer.parseInt((capacityTextField.getText())); //Handled by catch block

                if (!(yearTextField.getText().length() == 4)) { //Check if the year is length 4
                    Util.setMessageLabel("Aircraft not added. Year must be a four digit number.", Color.RED, messageLabel);
                }
                else {
                    Aircraft aircraft = new Aircraft();
                    aircraft.setAirline_id(airlineChoiceComboBox.getValue().getId());
                    aircraft.setSerial_number(serialNumberTextField.getText());
                    aircraft.setMake(makeTextField.getText());
                    aircraft.setModel(modelTextField.getText());
                    aircraft.setYear(year);
                    aircraft.setCapacity(capacity);

                    // disable buttons until a success/failure is received
                    mainGridPane.setDisable(true);
                    messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

                    int rowsAffected = aircraftOperator.insert(aircraft);

                    if (rowsAffected == 0) {
                        Util.setMessageLabel("Aircraft not added. The serial number is unique to an aircraft.", Color.RED, messageLabel);
                    } else {
                        clearComponents();
                        Util.setMessageLabel("Aircraft added.", Color.GREEN, messageLabel);
                    }
                    mainGridPane.setDisable(false);
                }
            }
            else {
                Util.setMessageLabel("Aircraft not added. Please fill the required fields.", Color.RED, messageLabel);
            }
        }
        catch (Exception e) {
            Util.setMessageLabel("Aircraft not added. Aircraft year and capacity must be numbers.", Color.RED, messageLabel);
        }

    }

    /**
     * Called when the user chooses a new option in the "Airline" ComboBox.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void airlineChosen(ActionEvent actionEvent) {
        Airline airlineChosen = airlineChoiceComboBox.getValue();
    }

    private void clearComponents() {
        airlineChoiceComboBox.setValue(null);
        serialNumberTextField.clear();
        makeTextField.clear();
        modelTextField.clear();
        yearTextField.clear();
        capacityTextField.clear();
    }

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     * The name of this method is the 'onAction' FXML value for the backButton.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void backButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }
}
