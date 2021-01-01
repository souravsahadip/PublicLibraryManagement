package public_library_management;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Dipcenation
 */
public class Registration {

    AnchorPane root = new AnchorPane();
    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    Scene prevscene;
    Stage primaryStage;

    public Registration(Stage primaryStage, Scene prevscene) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;
    }

    Scene getscene() {
        scene.setCursor(Cursor.DEFAULT);
        scene.setFill(Color.BLACK);
        {
            Button manageshelf = new Button();
            manageshelf.setText("Manage shelf for book");
            manageshelf.setLayoutX(250);
            manageshelf.setLayoutY(150);
            manageshelf.setVisible(false);

        }

        Button btn = new Button();
        btn.setText("Previous Page");
        btn.setLayoutX(100);
        btn.setLayoutY(150);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                LoginPage loginpage = new LoginPage(primaryStage);
                loginpage.sceneshow(prevscene);

            }
        });

        root.getChildren().addAll(btn);
        return scene;
    }

    void sceneshow() {

        primaryStage.setScene(this.getscene());

    }

}
