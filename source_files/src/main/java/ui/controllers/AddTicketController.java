package ui.controllers;

import database.operators.FlightOperator;
import database.operators.PassengerOnFlightOperator;
import database.operators.PassengerOperator;
import database.operators.TicketOperator;
import database.tables.Flight;
import database.tables.Passenger;
import database.tables.PassengerOnFlight;
import database.tables.Ticket;
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
import ui.converters.FlightStringConverter;
import ui.converters.PassengerOnFlightStringConverter;
import ui.converters.PassengerStringConverter;

import java.util.List;

public class AddTicketController {
    private TicketOperator ticketOperator = TicketOperator.getInstance();
    private FlightOperator flightOperator = FlightOperator.getInstance();
    private PassengerOnFlightOperator passengerOnFlightOperator = PassengerOnFlightOperator.getInstance();
    private PassengerOperator passengerOperator = PassengerOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private Button addButton;
    @FXML private ComboBox flightIdField;
    @FXML private ComboBox passengerIdField;
    @FXML private TextField seatClassTextField;
    @FXML private TextField seatTextField;
    @FXML private TextField priceTextField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        Platform.runLater(() -> backButton.getScene().getRoot().requestFocus());
        flightIdField.setConverter(new FlightStringConverter());
        passengerIdField.setConverter(new PassengerStringConverter());
        flightIdField.getItems().addAll(flightOperator.selectAll());
    }

    public void addTicketButtonClicked(ActionEvent actionEvent) {
        if (flightIdField.getValue() != null &&
                passengerIdField.getValue() != null &&
                seatClassTextField.getText() != null &&
                seatTextField.getText() != null &&
                priceTextField.getText() != null) {
            Flight chosenFlight = (Flight) flightIdField.getValue();
            Passenger chosenPassenger = (Passenger) passengerIdField.getValue();
            PassengerOnFlight pof = new PassengerOnFlight();
            pof.setFlight_id(chosenFlight.getId());
            pof.setPassenger_id(chosenPassenger.getId());
            passengerOnFlightOperator.insert(pof);
            Ticket ticket = new Ticket();
            ticket.setPassenger_on_flight_id(pof.getId());
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
        flightIdField.setValue(null);
        passengerIdField.setValue(null);
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

    public void flightChosen(ActionEvent actionEvent) {
        if (flightIdField.getValue() != null) {
            Flight flightChosen = (Flight) flightIdField.getValue();
            passengerIdField.getItems().clear();
            passengerIdField.setValue(null);
            List<PassengerOnFlight> passengerOnFlightObjects = null;
            for (PassengerOnFlight pof : passengerOnFlightObjects) {
                if (pof.getFlight_id() == flightChosen.getId())
                    passengerIdField.getItems().add(passengerOperator.selectById(pof.getPassenger_id()));
            }
        }
    }
}
