package ui.controllers;

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

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Abstract class representing basic data and methods that each database manipulating controller class has.
 */
public abstract class DatabaseManipulatingController implements Initializable {
    @FXML protected GridPane mainGridPane;
    @FXML protected GridPane tripleColumnGridPane;
    @FXML protected GridPane backButtonGridPane;
    @FXML protected Button backButton;
    @FXML protected Label messageLabel;
    private UserTypes activeUserType;

    protected void setActiveUserType(UserTypes activeUserType) {
        this.activeUserType = activeUserType;
    }

    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);
    /**
     * Called when the back button is clicked. Replaces the current screen with the main screen.
     * The name of this method is the 'onAction' FXML value for the backButton.
     *
     * @param actionEvent Event representing the action of the button firing, holds extra information.
     */
    public void backButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ButtonScreen.fxml"));
            Parent root = fxmlLoader.load();
            ButtonScreenController buttonScreenController = fxmlLoader.getController();
            buttonScreenController.setActiveUserType(activeUserType);

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Button Screen");
            stage.setWidth(UIConstants.VIEW_PREFERRED_WIDTH);
            stage.setHeight(UIConstants.VIEW_PREFERRED_HEIGHT);
            stage.setMinWidth(UIConstants.VIEW_MIN_WIDTH);
            stage.setMinHeight(UIConstants.VIEW_MIN_HEIGHT);
            stage.show();
            Launcher.closeStage();
        } catch (Exception ex) {
            Util.setMessageLabel("There was an issue loading the button screen.", Color.RED, messageLabel);
        }
    }

    /**
     * Used to unfreeze the screen after some database query has been run.
     */
    protected void enable()
    {
        mainGridPane.setDisable(false);
    }

    /**
     * Used to freeze the screen so some database query can run on another thread without the user modifying
     * any components.
     */
    protected void disable()
    {
        mainGridPane.setDisable(true);
        messageLabel.setText(UIConstants.CONTROLLER_QUERY_RUNNING_MESSAGE);
    }
}
