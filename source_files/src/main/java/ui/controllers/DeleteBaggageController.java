package ui.controllers;

import database.operators.BaggageOperator;
import database.operators.PassengerOperator;
import database.tables.Baggage;
import database.tables.Passenger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteBaggageController extends ThreeColumnController {
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();
    private BaggageOperator baggageOperator = BaggageOperator.getInstance();

    @FXML private ComboBox<Passenger> passengerComboBox;
    @FXML private ComboBox<Baggage> baggageComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();
        });
        passengerComboBox.getItems().addAll(passengerOperator.selectAll());
    }

    public void deleteBaggageButtonClicked() {
        if (passengerComboBox.getValue() != null && baggageComboBox.getValue() != null) {
            disable();

            int rowsAffected = baggageOperator.deleteById(baggageComboBox.getValue().getId());

            if (rowsAffected == 0) {
                Util.setMessageLabel("Baggage not deleted.", Color.RED, messageLabel); // TODO for what reason could this happen?
            } else {
                updateBaggageComboBox();
                Util.setMessageLabel("Baggage deleted.", Color.GREEN, messageLabel);
            }
            enable();
        } else {
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

    private void updateBaggageComboBox() {
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
}
