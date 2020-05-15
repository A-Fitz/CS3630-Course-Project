package ui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;
import ui.UserTypes;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class ButtonScreenController implements Initializable {
    private UserTypes activeUserType;
    @FXML private GridPane startGridPane;
    @FXML private Label messageLabel;
    @FXML private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::addButtons);
    }

    public void setActiveUserType(UserTypes activeUserType) {
        this.activeUserType = activeUserType;
    }

    private void addButtons() {
        switch (activeUserType) {
            case AIRLINE_EMPLOYEE:
                createAirlineEmployeeButtons();
                break;
            case AIRPORT_EMPLOYEE:
                createAirportEmployeeButtons();
                break;
            case ADMINISTRATOR:
                createAdminButtons();
                break;
        }
    }

    private void createAirlineEmployeeButtons() {
        Button b1 = new Button("Add Airline");
        b1.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b1, 0, 0, 1, 1);
        b1.setOnAction(e -> changeScreen("/AddAirlineScreen.fxml", "Add Airline"));

        Button b2 = new Button("Edit Airline");
        b2.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b2, 1, 0, 1, 1);
        b2.setOnAction(e -> changeScreen("/EditAirlineScreen.fxml", "Edit Airline"));

        Button b3 = new Button("Add Flight");
        b3.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b3, 2, 0, 1, 1);
        b3.setOnAction(e -> changeScreen("/AddFlightScreen.fxml", "Add Flight"));

        Button b4 = new Button("Edit Flight");
        b4.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b4, 3, 0, 1, 1);
        b4.setOnAction(e -> changeScreen("/EditFlightScreen.fxml", "Edit Flight"));

        Button b5 = new Button("Add Ticket");
        b5.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b5, 0, 1, 1, 1);
        b5.setOnAction(e -> changeScreen("/AddTicketScreen.fxml", "Add Ticket"));

        Button b6 = new Button("Delete Passenger On Flight");
        b6.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b6, 1, 1, 1, 1);
        b6.setOnAction(e -> changeScreen("/DeletePassengerOnFlightScreen.fxml", "Delete Passenger On Flight"));

        Button b7 = new Button("Add Airline Employee To Flight");
        b7.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b7, 2, 1, 1, 1);
        b7.setOnAction(e -> changeScreen("/AddAirlineEmployeeToFlightScreen.fxml", "Add Airline Employee To Flight"));
    }


    private void createAirportEmployeeButtons() {
        Button b1 = new Button("Add Airport");
        b1.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b1, 0, 0, 1, 1);
        b1.setOnAction(e -> changeScreen("/AddAirportScreen.fxml", "Add Airport"));

        Button b2 = new Button("Add Aircraft");
        b2.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b2, 1, 0, 1, 1);
        b2.setOnAction(e -> changeScreen("/AddAircraftScreen.fxml", "Add Aircraft"));

        Button b3 = new Button("Add Baggage");
        b3.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b3, 2, 0, 1, 1);
        b3.setOnAction(e -> changeScreen("/AddBaggageScreen.fxml", "Add Baggage"));

        Button b4 = new Button("Delete Baggage");
        b4.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b4, 3, 0, 1, 1);
        b4.setOnAction(e -> changeScreen("/DeleteBaggageScreen.fxml", "Delete Baggage"));

        Button b5 = new Button("Add Passenger");
        b5.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b5, 0, 1, 1, 1);
        b5.setOnAction(e -> changeScreen("/AddPassengerScreen.fxml", "Add Passenger"));
    }

    private void createAdminButtons() {
        Button b1 = new Button("Add Employee");
        b1.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b1, 0, 0, 1, 1);
        b1.setOnAction(e -> changeScreen("/AddEmployeeScreen.fxml", "Add Employee"));

        Button b2 = new Button("Add Job Type");
        b2.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b2, 1, 0, 1, 1);
        b2.setOnAction(e -> changeScreen("/AddJobTypeScreen.fxml", "Add Job Type"));

        Button b3 = new Button("Add Baggage Status Type");
        b3.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b3, 2, 0, 1, 1);
        b3.setOnAction(e -> changeScreen("/AddBaggageStatusTypeScreen.fxml", "Add Baggage Status Type"));

        Button b4 = new Button("Edit Flight Status Type");
        b4.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b4, 3, 0, 1, 1);
        b4.setOnAction(e -> changeScreen("/AddFlightStatusTypeScreen.fxml", "Edit Flight Status Type"));

        Button b5 = new Button("Add Seat Class Type");
        b5.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b5, 0, 1, 1, 1);
        b5.setOnAction(e -> changeScreen("/AddSeatClassTypeScreen.fxml", "Add Seat Class Type"));

        Button b6 = new Button("Add Ticket Status Type");
        b6.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        startGridPane.add(b6, 1, 1, 1, 1);
        b6.setOnAction(e -> changeScreen("/AddTicketStatusTypeScreen.fxml", "Add Ticket Status Type"));
    }

    /**
     * Changes from this screen to the screen specified by the fxml given with the title given.
     *
     * @param resourcePath The fxml file which represents a screen.
     * @param title        The title of the new screen.
     */
    private void changeScreen(String resourcePath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resourcePath));
            Parent root = fxmlLoader.load();
            ((DatabaseManipulatingController)fxmlLoader.getController()).setActiveUserType(activeUserType);

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setWidth(UIConstants.VIEW_PREFERRED_WIDTH);
            stage.setHeight(UIConstants.VIEW_PREFERRED_HEIGHT);
            stage.setMinWidth(UIConstants.VIEW_MIN_WIDTH);
            stage.setMinHeight(UIConstants.VIEW_MIN_HEIGHT);
            stage.show();
            Launcher.closeStage();
        } catch (Exception ex) {
            Util.setMessageLabel("There was an issue loading the " + title + " screen.", Color.RED, messageLabel);
        }
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.showStage();
    }
}
