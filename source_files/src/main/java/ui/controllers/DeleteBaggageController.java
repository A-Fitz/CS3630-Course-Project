package ui.controllers;

import database.operators.AirlineOperator;
import database.operators.BaggageOperator;
import database.operators.PassengerOperator;
import database.tables.Airline;
import database.tables.Baggage;
import database.tables.Passenger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class DeleteBaggageController {
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();
    private BaggageOperator baggageOperator = BaggageOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private ComboBox<Passenger> passengerComboBox;
    @FXML private ComboBox<Baggage> baggageComboBox;
    @FXML private Label messageLabel;

    public void initialize() {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();
        });
        passengerComboBox.getItems().addAll(passengerOperator.selectAll());
    }

    public void deleteBaggageButtonClicked()
    {
        if(passengerComboBox.getValue() != null && baggageComboBox.getValue() != null)
        {
            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            int rowsAffected = baggageOperator.deleteById(baggageComboBox.getValue().getId());

            if (rowsAffected == 0) {
                Util.setMessageLabel("Baggage not deleted.", Color.RED, messageLabel); // TODO for what reason could this happen?
            } else {
                updateBaggageComboBox();
                Util.setMessageLabel("Baggage deleted.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
        }
        else
        {
            Util.setMessageLabel("Error. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Called when the passenger which has baggage to be removed is chosen. Should fill the BaggageComboBox with their baggage.
     *
     * @param actionEvent Event representing the action of the combobox choice being chosen, holds extra information.
     */
    public void passengerChosen(ActionEvent actionEvent) {
        updateBaggageComboBox();
    }

    private void updateBaggageComboBox()
    {
        clearAllFields();
        baggageComboBox.getItems().addAll(baggageOperator.selectByPassengerId(passengerComboBox.getValue().getId()));
    }

    /**
     * Clears all user-operable fields on the screen.
     */
    private void clearAllFields() {
        baggageComboBox.setValue(null);
        baggageComboBox.getItems().clear();
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
