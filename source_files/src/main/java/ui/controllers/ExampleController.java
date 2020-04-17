package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ui.Launcher;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class representing an example controller for others to use. Is related to the resources/ExampleScreen.fxml FXML file for the UI.
 */
public class ExampleController implements Initializable
{
    // Put your JavaFX components here. Buttons, Labels, TextFields, etc. The name of this variable will be the fx:id of the component in FXML.
    // Follow camelCase naming with the component type as the last phrase in the word. (i.e. 'Button' is the last phrase)
    @FXML private Button backButton; // every controller should have this button

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // this is where your code for doing any programatic ui adjustments should go

        // for example I could change the text of the back button (you don't need to do this)
        backButton.setText("test back");
    }

    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     * The name of this method is the 'onAction' FXML value for the backButton.
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void backButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        Launcher.stage.show();
    }
}
