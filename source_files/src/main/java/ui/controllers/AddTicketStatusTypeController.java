package ui.controllers;

import database.operators.TicketStatusTypeOperator;
import database.tables.TicketStatusType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.dao.DataAccessException;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class representing an example controller for others to use. Is related to the resources/ExampleScreen.fxml FXML file for the UI.
 */
public class AddTicketStatusTypeController extends ThreeColumnController {
    TicketStatusTypeOperator ticketStatusTypeOperator = TicketStatusTypeOperator.getInstance();

    @FXML private TextField titleTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
        titleTextField.setText(null);
    }

    public void addTicketStatusTypeButtonClicked(ActionEvent actionEvent) {

        if (titleTextField.getText() != null) {
            // Disable interacting with components while the operator is running. Must be done for multithreaded environment.
            disable();

            TicketStatusType ticketStatusType = new TicketStatusType();
            ticketStatusType.setTitle(titleTextField.getText());

            try {
                ticketStatusTypeOperator.insert(ticketStatusType);
            }

            catch (DataAccessException dae) {
                Util.setMessageLabel("Something went wrong.", Color.RED, messageLabel);
                enable();
                return;
            }
                clearAllFields();
                Util.setMessageLabel("Ticket status type added.", Color.GREEN, messageLabel);

        } else {
            Util.setMessageLabel("Ticket status type not added. Please fill all fields.", Color.RED, messageLabel);
        }
        enable();
    }

    private void clearAllFields() {
        titleTextField.setText(null);
    }
}

