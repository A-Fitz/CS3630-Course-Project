package ui.controllers;

import database.tables.SystemUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ui.Launcher;
import ui.UIConstants;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class RunTimeStartScreenController extends ThreeColumnController {
    private SystemUser activeUser=null;

    @FXML
    private GridPane startGridPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->addButtons());
    }

    public void setActiveUser(SystemUser activeUser) {
        this.activeUser = activeUser;
    }

    public SystemUser getActiveUser()
    {
        return activeUser;
    }

    private void addButtons() {
        //region id =1 if airport
        if (activeUser.getUserRegion() == 1)
            switch (activeUser.getUserJobType()) {
                case 1:
                    loadAirportJob1Screen();
                    break;
                case 2:
                    loadAirportJob2Screen();
                    break;
                case 3:
                    loadAirportJob3Screen();
                    break;
            }
            //region id =2 if airline
        else if (activeUser.getUserRegion() == 2) {
            switch (activeUser.getUserJobType()) {
                case 1:
                    loadAirlineJob1Screen();
                    break;
                case 2:
                    loadAirlineJob2Screen();
                    break;
                case 3:
                    loadAirlineJob3Screen();
                    break;
            }
        }
    }


    private void loadAirportJob1Screen()
    {
        Button b1=new Button("Add Baggage");
        b1.setPrefSize(272,88);
        startGridPane.add(b1,0,0,1,1);
        b1.setOnAction(e->changeScreen("src/main/resources/AddBaggageScreen.fxml", "Add Baggage"));

        Button b2=new Button("Add Baggage Status Type");
        b2.setPrefSize(272,88);
        startGridPane.add(b2,1,0,1,1);
        b2.setOnAction(e->changeScreen("src/main/resources/AddBaggageStatusTypeScreen.fxml", "Add Baggage Status Type"));

        Button b3=new Button("Delete Baggage");
        b3.setPrefSize(272,88);
        startGridPane.add(b3,2,0,1,1);
        b3.setOnAction(e->changeScreen("src/main/resources/DeleteBaggageScreen.fxml", "Delete Baggage"));
    }
    private void loadAirportJob2Screen()
    {
        //TODO add business logic

    }
    private void loadAirportJob3Screen()
    {
        //TODO add business logic
    }

    private void loadAirlineJob1Screen()
    {
        Button b1=new Button("Add Passenger");
        b1.setPrefSize(272,88);
        startGridPane.add(b1,0,0,1,1);
        b1.setOnAction(e->changeScreen("src/main/resources/AddPassengerScreen.fxml", "Add Passenger"));

        Button b2=new Button("Add Ticket");
        b2.setPrefSize(272,88);
        startGridPane.add(b2,1,0,1,1);
        b2.setOnAction(e->changeScreen("src/main/resources/AddTicketScreen.fxml", "Add Ticket"));

    }
    private void loadAirlineJob2Screen()
    {
        Button b1=new Button("Add Aircraft");
        b1.setPrefSize(272,88);
        startGridPane.add(b1,0,0,1,1);
        b1.setOnAction(e->changeScreen("src/main/resources/AddAircraftScreen.fxml", "Add Aircraft"));

    }
    private void loadAirlineJob3Screen()
    {
        Button b1=new Button("Add Flight");
        b1.setPrefSize(272,88);
        startGridPane.add(b1,0,0,1,1);
        b1.setOnAction(e->changeScreen("src/main/resources/AddFlightScreen.fxml", "Add Flight"));

        Button b2=new Button("Edit Flight");
        b2.setPrefSize(272,88);
        startGridPane.add(b2,1,0,1,1);
        b2.setOnAction(e->changeScreen("src/main/resources/EditFlightScreen.fxml", "Edit Flight"));
    }


    /**
     * Changes from this screen to the screen specified by the fxml given with the title given.
     *
     * @param resourcePath The fxml file which represents a screen.
     * @param title        The title of the new screen.
     */
    private void changeScreen(String resourcePath, String title) {
        try {

            FileInputStream fileInputStream = new FileInputStream(new File(resourcePath));
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fileInputStream);
            ThreeColumnController controller = loader.getController();
            controller.setActiveUser(activeUser);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setWidth(UIConstants.VIEW_PREFERRED_WIDTH);
            stage.setHeight(UIConstants.VIEW_PREFERRED_HEIGHT);
            stage.setMinWidth(UIConstants.VIEW_MIN_WIDTH);
            stage.setMinHeight(UIConstants.VIEW_MIN_HEIGHT);
            stage.show();
            Launcher.closeStage();

            Stage thisStage = (Stage) startGridPane.getScene().getWindow();
            thisStage.close();

        } catch (Exception ex) {
            ex.printStackTrace(); // TODO better exception handling
        }
    }

}
