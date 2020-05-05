package ui.controllers;

import database.operators.*;
import database.tables.base.AirlineJobType;
import database.tables.base.AirportJobType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class AddJobTypeController {

    private AirportJobTypeOperator airportJobTypeOperator = AirportJobTypeOperator.getInstance();
    private AirlineJobTypeOperator airlineJobTypeOperator = AirlineJobTypeOperator.getInstance();

    @FXML private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private ComboBox<String> regionComboBox;
    @FXML private TextField jobTypeTextField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();
        });
    }

    public void addJobTitleButtonClicked()
    {
        if(regionComboBox.getValue()!=null &&
                jobTypeTextField.getText()!= null)
        {
            if (regionComboBox.getValue().equals("Airport"))
            {
                setAirportJobType();
            }
            else if (regionComboBox.getValue().equals("Airline"))
            {
                setAirlineJobType();
            }
        }
        else
        {
            Util.setMessageLabel("Error. Please fill the required fields.", Color.RED, messageLabel);
        }
    }

    private void setAirportJobType() {
        AirportJobType airportJobType = new AirportJobType();
        airportJobType.setTitle(jobTypeTextField.getText());
        mainGridPane.setDisable(true);
        messageLabel.setText("Request in progress...");
        int rowsAffected = airportJobTypeOperator.insert(airportJobType);

        if (rowsAffected == 0)
        {
            Util.setMessageLabel("Airport Job Title not added.", Color.RED, messageLabel);
        }
        else {
            clearAllFields();
            Util.setMessageLabel("Airport Job Title added.", Color.GREEN, messageLabel);
        }
        mainGridPane.setDisable(false);
    }

    private void setAirlineJobType() {
        AirlineJobType airlineJobType = new AirlineJobType();
        airlineJobType.setTitle(jobTypeTextField.getText());
        mainGridPane.setDisable(true);
        messageLabel.setText("Request in progress...");
        int rowsAffected = airlineJobTypeOperator.insert(airlineJobType);

        if (rowsAffected == 0)
        {
            Util.setMessageLabel("Airline Job Title not added.", Color.RED, messageLabel);
        }
        else {
            clearAllFields();
            Util.setMessageLabel("Airline Job Title added.", Color.GREEN, messageLabel);
        }
        mainGridPane.setDisable(false);
    }


    /**
     * Use this event to limit the choices for the workPlaceComboBox and jobTypeComboBox.
     * @param actionEvent Event representing the action of the ComboBox item being clicked, holds extra information.
     */
    public void regionChosen(ActionEvent actionEvent) {
        clearAllFields();

        if(regionComboBox.getValue().equals("Airport"))
        {

        }
        else if(regionComboBox.getValue().equals("Airline"))
        {

        }
    }

    /**
     * Clears all user-operable fields on the screen.
     */
    private void clearAllFields() {
        jobTypeTextField.clear();
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
