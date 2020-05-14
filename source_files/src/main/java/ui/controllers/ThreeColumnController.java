package ui.controllers;

import database.tables.SystemUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Abstract class representing basic data and methods that each three column controller class has.
 */
public abstract class ThreeColumnController implements Initializable {
    private SystemUser activeUser=null;
    @FXML protected GridPane mainGridPane;
    @FXML protected GridPane tripleColumnGridPane;
    @FXML protected GridPane backButtonGridPane;
    @FXML protected Button backButton;
    @FXML protected Label messageLabel;

    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);

    public void setActiveUser(SystemUser activeUser){
        this.activeUser=activeUser;
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

        try {
            String fxmlPath = "src/main/resources/RunTimeStartScreen.fxml";
            FileInputStream fileInputStream = new FileInputStream(new File(fxmlPath));
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fileInputStream);
            RunTimeStartScreenController controller = loader.getController();
            controller.setActiveUser(activeUser);



            Stage runTimeStage = new Stage();
            runTimeStage.setScene(new Scene(root));
            runTimeStage.setWidth(UIConstants.VIEW_PREFERRED_WIDTH);
            runTimeStage.setHeight(UIConstants.VIEW_PREFERRED_HEIGHT);
            runTimeStage.setMinWidth(UIConstants.VIEW_MIN_WIDTH);
            runTimeStage.setMinHeight(UIConstants.VIEW_MIN_HEIGHT);
            runTimeStage.show();
            Launcher.closeStage();
        } catch (Exception ex) {
            ex.printStackTrace(); // TODO better exception handling
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
