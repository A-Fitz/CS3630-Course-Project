package ui.controllers;

import database.operators.AircraftOperator;
import database.operators.AirlineOperator;
import database.tables.Aircraft;
import database.tables.Airline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.*;
import ui.exceptions.IncorrectInputException;
import ui.formatters.IntegerTextFormatter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Add Aircraft view.
 */
public class AddAircraftController implements Initializable {
    private AircraftOperator aircraftOperator = AircraftOperator.getInstance();
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Label messageLabel;
    @FXML private ComboBox<Airline> airlineChoiceComboBox;
    @FXML private TextField serialNumberTextField;
    @FXML private TextField makeTextField;
    @FXML private TextField modelTextField;
    @FXML private TextField yearTextField;
    @FXML private TextField capacityTextField;

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

                Aircraft aircraft = new Aircraft();
                aircraft.setAirline_id(airlineChoiceComboBox.getValue().getId());
                aircraft.setSerial_number(serialNumberTextField.getText());
                aircraft.setMake(makeTextField.getText());
                aircraft.setModel(modelTextField.getText());
                aircraft.setYear(Integer.parseInt(yearTextField.getText()));
                aircraft.setCapacity(Integer.parseInt((capacityTextField.getText())));

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
