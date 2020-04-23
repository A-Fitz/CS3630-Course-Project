package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Launcher;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the base screen of the system. It has waypoints to other screens in the form of buttons.
 */
public class StartScreenController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
            Launcher.closeStage();
        } catch (Exception ex) {
            ex.printStackTrace(); // TODO better exception handling
        }
    }

    // TODO see StartScreen.fxml for more button events. Feel free to add, change, and remove buttons

    public void editFlightButtonClicked(ActionEvent actionEvent) {
        changeScreen("/EditFlightScreen.fxml", "Edit Flight");
    }

    public void addAirlineButtonClicked(ActionEvent actionEvent) {
        changeScreen("/AddAirlineScreen.fxml", "Add Airline");
    }

    public void addTicketButtonClicked(ActionEvent actionEvent) {
        changeScreen("/AddTicketScreen.fxml", "Add Ticket");
    }

    public void addAirportButtonClicked(ActionEvent actionEvent) {
        changeScreen("/AddAirportScreen.fxml", "Add Airport");
    }

    public void addAircraftButtonClicked(ActionEvent actionEvent) {
        changeScreen("/AddAircraftScreen.fxml", "Add Aircraft");
    }

    public void addPassengerButtonClicked(ActionEvent actionEvent) {
        changeScreen("/AddPassengerScreen.fxml", "Add Passenger");
    }

    public void addBaggageButtonClicked(ActionEvent actionEvent) {
        changeScreen("/AddBaggageScreen.fxml", "Add Baggage");
    }

    public void addEmployeeButtonClicked(ActionEvent actionEvent) {
        changeScreen("/AddEmployeeScreen.fxml", "Add Employee");
    }
}
