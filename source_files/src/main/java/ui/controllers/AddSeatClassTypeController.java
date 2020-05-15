package ui.controllers;

import database.operators.SeatClassTypeOperator;
import database.tables.SeatClassType;
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
 * Class representing an example controller for others to use. Is related to the resources/ExampleScreen.fxml FXML file for the UI.
 */
public class AddSeatClassTypeController extends DatabaseManipulatingController {
    SeatClassTypeOperator seatClassTypeOperator = SeatClassTypeOperator.getInstance();

    // Put your JavaFX components here. Buttons, Labels, TextFields, etc. The name of this variable will be the fx:id of the component in FXML.
    // Follow camelCase naming with the component type as the last phrase in the word (i.e. 'Button' is the last phrase).
    // Each controller should have the following components.


    // The following are example components.
    @FXML private TextField titleTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
        titleTextField.setText(null);
    }

    /**
     * Called when the "do something" button is clicked. Use a method like this to check if their input is valid and
     * then try the operation. If it works display a success message, if not display an error message.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void addSeatClassTypeButtonClicked(ActionEvent actionEvent) {

        if (titleTextField.getText() != null) {
            // Disable interacting with components while the operator is running. Must be done for multithreaded environment.
            disable();

            SeatClassType seatClassType = new SeatClassType();
            seatClassType.setTitle(titleTextField.getText());

            try {
                seatClassTypeOperator.insert(seatClassType);

                clearAllFields();
                Util.setMessageLabel("Seat class type added.", Color.GREEN, messageLabel);
            }
                catch(DuplicateKeyException dke){
                    Util.setMessageLabel("Seat class type not added. The title is unique to a seat class type.", Color.RED, messageLabel);
                } catch(DataAccessException dae){
                    Util.setMessageLabel("There was a failure while accessing related data in the database. Please try again.", Color.RED, messageLabel);
                } catch(Exception e){
                    Util.setMessageLabel("There was a major failure during this operation.", Color.RED, messageLabel);
                }
            enable();
            } else {
            Util.setMessageLabel("Seat class type not added. Please fill all fields.", Color.RED, messageLabel);
        }
    }

    private void clearAllFields() {
        titleTextField.setText(null);
    }


}

