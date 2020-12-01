/**
 * Author - Thomas Bowidowicz
 * CS351L - Project 2 - Mexican Train Dominoes
 *
 * This is the main class and method that run the GUI version of the game.
 * It simply sets the parent and stage and executes the launch.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class and method that run the GUI version of the game.
 * It simply sets the parent and stage and executes the launch.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Mexican Train Dominoes");
        primaryStage.setScene(new Scene(root, 1400, 1000));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
