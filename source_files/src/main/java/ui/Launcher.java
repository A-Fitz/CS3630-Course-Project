package ui;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

/**
 * This class functions only as a test launcher. Real implementation will overwrite it.
 */
public class Launcher extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) {
        Launcher.stage = stage;

        // create loading dialog
        Alert loadingDialog = new Alert(Alert.AlertType.INFORMATION);
        loadingDialog.setHeaderText(null);
        loadingDialog.setTitle("Airport Management System");
        loadingDialog.setContentText("Airport Management System is loading... Please Wait");
        loadingDialog.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
        loadingDialog.initStyle(StageStyle.UNDECORATED);

        task.setOnRunning((e) -> loadingDialog.show());
        task.setOnSucceeded((e) -> {
            loadingDialog.hide();
            try {
                // TODO this is where we show the main screen
                String javaVersion = System.getProperty("java.version");
                String javafxVersion = System.getProperty("javafx.version");
                Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
                Scene scene = new Scene(new StackPane(l), 640, 480);
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace(); // TODO better exception handling
            }
        });
        new Thread(task).start();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * This task is used to make a connection to the database before the program can be used by a user.
     */
    private Task<Boolean> task = new Task<>() {
        @Override
        public Boolean call() throws SQLException {
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            return databaseConnection.operate();
        }
    };
}
