package ui.controllers;

import database.operators.TicketStatusTypeOperator;
import database.tables.base.TicketStatusType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class representing an example controller for others to use. Is related to the resources/ExampleScreen.fxml FXML file for the UI.
 */
public class AddTicketStatusTypeController implements Initializable {
    TicketStatusTypeOperator ticketStatusTypeOperator = TicketStatusTypeOperator.getInstance();

    // Put your JavaFX components here. Buttons, Labels, TextFields, etc. The name of this variable will be the fx:id of the component in FXML.
    // Follow camelCase naming with the component type as the last phrase in the word (i.e. 'Button' is the last phrase).
    // Each controller should have the following components.
    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

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
    public void addTicketStatusTypeButtonClicked(ActionEvent actionEvent) {

        if (titleTextField.getText() != null) {
            // Disable interacting with components while the operator is running. Must be done for multithreaded environment.
            mainGridPane.setDisable(true);
            messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);

            TicketStatusType ticketStatusType = new TicketStatusType();
            ticketStatusType.setTitle(titleTextField.getText());

            int rowsAffected = ticketStatusTypeOperator.insert(ticketStatusType);

            if (rowsAffected == 0)
                Util.setMessageLabel("Something went wrong.", Color.RED, messageLabel);
            else {
                clearAllFields();
                Util.setMessageLabel("Ticket status type added.", Color.GREEN, messageLabel);
            }

        } else {
            Util.setMessageLabel("Ticket status type not added. Please fill all fields.", Color.RED, messageLabel);
        }
        mainGridPane.setDisable(false);
    }

    private void clearAllFields() {
        titleTextField.setText(null);
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

