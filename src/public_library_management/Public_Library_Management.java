package public_library_management;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Dipcenation
 */
public class Public_Library_Management extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginPage loginpage = new LoginPage(primaryStage);
        Scene scene = loginpage.getscene();

        primaryStage.setTitle("Pubilc Library");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
