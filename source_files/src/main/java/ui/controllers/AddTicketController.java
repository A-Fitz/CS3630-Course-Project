package ui.controllers;

import database.operators.TicketOperator;
import database.tables.Ticket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddTicketController {
    private TicketOperator ticketOperator = TicketOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Button addButton;
    @FXML private TextField passengerOnFlightIdTextField;
    @FXML private TextField seatClassTextField;
    @FXML private TextField seatTextField;
    @FXML private TextField priceTextField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
    }

    public void addTicketButtonClicked(ActionEvent actionEvent) {
        if (passengerOnFlightIdTextField.getText() != null &&
                seatClassTextField.getText() != null &&
                seatTextField.getText() != null &&
                priceTextField.getText() != null) {
            Ticket ticket = new Ticket();
            ticket.setPassenger_on_flight_id(Integer.valueOf(passengerOnFlightIdTextField.getText()));
            ticket.setSeat_class_id(Integer.valueOf(seatClassTextField.getText()));
            ticket.setSeat(seatClassTextField.getText());
            ticket.setPrice(Float.valueOf(priceTextField.getText()));


            // disable buttons until a success/failure is received
            mainGridPane.setDisable(true);
            messageLabel.setText("Request in progress...");

            int rowsAffected = ticketOperator.insert(ticket);

            if (rowsAffected == 0) {
                // ticket not inserted (probably due to unique constraints on abbreviation or name). Display error message.
                ui.Util.setMessageLabel("Ticket not added.", Color.RED, messageLabel);
            } else {
                // ticket inserted. Clear each text field and display success message.
                clearAllTextFields();
                ui.Util.setMessageLabel("Ticket added.", Color.GREEN, messageLabel);
            }
            mainGridPane.setDisable(false);
        } else {
            // All fields must not be null. Display error message.
            ui.Util.setMessageLabel("Ticket not added. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    /**
     * Clears all user-operable text fields on the screen.
     */
    private void clearAllTextFields() {
        passengerOnFlightIdTextField.clear();
        seatClassTextField.clear();
        seatTextField.clear();
        priceTextField.clear();

    }

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     */
    public void backButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        ui.Launcher.showStage();
    }
}
