package ui.controllers;

import database.operators.BaggageStatusTypeOperator;
import database.tables.BaggageStatusType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the "Add Baggage Status Type" screen. Will allow for inserting a baggage status type into the database given valid input.
 */
public class AddBaggageStatusTypeController extends DatabaseManipulatingController {
    private BaggageStatusTypeOperator baggageStatusTypeOperator = BaggageStatusTypeOperator.getInstance();

    @FXML private TextField baggageStatusTypeTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
    }

    /**
     * Called when the "Add Baggage Status Type" button is clicked. Handles verifying user input so that a new baggage
     * status type can be inserted into the database. Shows success and error messages.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addBaggageStatusTypeButtonClicked(ActionEvent actionEvent) {
        if (baggageStatusTypeTextField.getText() != null && !baggageStatusTypeTextField.getText().isEmpty()) {
            // disable buttons until a success/failure is received
            disable();

            BaggageStatusType baggageStatusType = new BaggageStatusType();
            baggageStatusType.setTitle(baggageStatusTypeTextField.getText());

            try {
                baggageStatusTypeOperator.insert(baggageStatusType);

                // Baggage status type inserted. Clear each text field and display success message.
                clearAllTextFields();
                Util.setMessageLabel("Baggage status type added.", Color.GREEN, messageLabel);
            }
            catch (DuplicateKeyException dke) {
                Util.setMessageLabel("Baggage status type not added. The title is unique to a baggage status type.", Color.RED, messageLabel);
            } catch (DataAccessException dae) {
                Util.setMessageLabel("There was a failure while accessing related data in the database. Please try again.", Color.RED, messageLabel);
            } catch (Exception e) {
                Util.setMessageLabel("There was a major failure during this operation.", Color.RED, messageLabel);
            }
            enable();
        } else {
            // All fields must not be null. Display error message.
            Util.setMessageLabel("Baggage status type not added. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Clears all user-operable text fields on the screen.
     */
    private void clearAllTextFields() {
        baggageStatusTypeTextField.clear();
    }
}
