package ui.controllers;

import database.operators.*;
import database.tables.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.Launcher;
import ui.Util;
import ui.converters.AirlineJobTypeStringConverter;
import ui.converters.AirlineStringConverter;
import ui.converters.AirportJobTypeStringConverter;
import ui.converters.AirportStringConverter;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {
    private AirportOperator airportOperator = AirportOperator.getInstance();
    private AirlineOperator airlineOperator = AirlineOperator.getInstance();
    private AirportJobTypeOperator airportJobTypeOperator = AirportJobTypeOperator.getInstance();
    private AirlineJobTypeOperator airlineJobTypeOperator = AirlineJobTypeOperator.getInstance();

    @FXML
    private GridPane mainGridPane;
    @FXML private Button backButton;
    @FXML private ComboBox<String> regionComboBox;
    @FXML private ComboBox workPlaceComboBox;
    @FXML private ComboBox jobTypeComboBox;
    @FXML private TextField firstNameTextField;
    @FXML private TextField middleNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;
    @FXML private DatePicker birthDateDatePicker;
    @FXML private Label messageLabel;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
        {
            backButton.getScene().getRoot().requestFocus();
            regionComboBox.getItems().add("Airport");
            regionComboBox.getItems().add("Airline");
        });
    }

    public void addEmployeeButtonClicked() {
        // TODO @jason. By the way, ignore the warnings in the regionChosen method. It doesn't like that we're using ComboBoxes for two different class types.
    }

    /**
     * Use this event to limit the choices for the workPlaceComboBox and jobTypeComboBox.
     * @param actionEvent Event representing the action of the ComboBox item being clicked, holds extra information.
     */
    public void regionChosen(ActionEvent actionEvent) {
        workPlaceComboBox.getItems().clear();

        if(regionComboBox.getValue().equals("Airport"))
        {
            workPlaceComboBox.getItems().addAll(airportOperator.selectAll());
            workPlaceComboBox.setConverter(new AirportStringConverter());
            jobTypeComboBox.getItems().addAll(airportJobTypeOperator.selectAll());
            jobTypeComboBox.setConverter(new AirportJobTypeStringConverter());
        }
        else if(regionComboBox.getValue().equals("Airline"))
        {
            workPlaceComboBox.getItems().addAll(airlineOperator.selectAll());
            workPlaceComboBox.setConverter(new AirlineStringConverter());
            jobTypeComboBox.getItems().addAll(airlineJobTypeOperator.selectAll());
            jobTypeComboBox.setConverter(new AirlineJobTypeStringConverter());
        }
    }

    /**
     * Clears all user-operable fields on the screen.
     */
    private void clearAllFields() {
        regionComboBox.getItems().clear();
        workPlaceComboBox.getItems().clear();
        firstNameTextField.clear();
        middleNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        addressTextField.clear();
        phoneTextField.clear();
        birthDateDatePicker.setValue(null);
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
