package ui.controllers;

import database.operators.AirlineOperator;
import database.tables.Airline;
import database.tables.Flight;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;
import ui.Util;

public class EditAirlineController {
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private ComboBox<Airline> airlineChoiceComboBox;
    @FXML private TextField nameTextField;
    @FXML private TextField abbreviationTextField;
    @FXML private Label messageLabel;

    public void initialize() {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();
            updateAirlineComboBox();
        });
    }

    public void editAirlineButtonClicked()
    {
        if(airlineChoiceComboBox.getValue()!=null &&
                nameTextField.getText()!= null &&
                abbreviationTextField.getText()!= null)
        {
            Airline airlineChosen = airlineChoiceComboBox.getValue();
            Airline newAirline=new Airline();
            newAirline.setAbbreviation(abbreviationTextField.getText());
            newAirline.setName(nameTextField.getText());

            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            int rowsAffected = airlineOperator.updateById(airlineChosen.getId(), newAirline);

            if (rowsAffected == 0) {
                Util.setMessageLabel("Airline not updated. Both the abbreviation and name fields are unique to an airline.", Color.RED, messageLabel);
            } else {
                clearAllFields();
                updateAirlineComboBox();
                Util.setMessageLabel("Airline updated.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
        }
        else
        {
            Util.setMessageLabel("Error. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    private void updateAirlineComboBox()
    {
        airlineChoiceComboBox.getItems().addAll(airlineOperator.selectAll());
    }

    /**
     * Called when the airline which is to be edited is chosen. Should fill the values of the TextFields and ComboBoxes
     * with what the airline currently has.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void airlineChosen(ActionEvent actionEvent) {
        nameTextField.setText(airlineChoiceComboBox.getValue().getName());
        abbreviationTextField.setText(airlineChoiceComboBox.getValue().getAbbreviation());
    }

    /**
     * Clears all user-operable fields on the screen.
     */
    private void clearAllFields() {
        airlineChoiceComboBox.getItems().clear();
        airlineChoiceComboBox.setValue(null);
        nameTextField.clear();
        abbreviationTextField.clear();
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
