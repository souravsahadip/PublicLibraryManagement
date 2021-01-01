package public_library_management;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author KIshore
 */
public class BookList {
    String email;
    Pane root = new Pane();
    ScrollPane sp = new ScrollPane();

    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    Scene prevscene;
    Stage primaryStage;
    ResultSet rs;
    int bookCount;

    public BookList(Stage primaryStage, Scene prevscene, ResultSet rs, int bookCount) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;
        this.rs = rs;
        this.bookCount = bookCount;

    }

    Scene getscene() {
        try {
            scene.setCursor(Cursor.DEFAULT);
            scene.setFill(Color.BLACK);
            VBox vbox = new VBox(80);

            Button tx[] = new Button[bookCount];
            int i = 0;

            while (i < bookCount) {
                rs.next();
                System.out.println("entered" + i);
                tx[i] = new Button();
                String bookName = rs.getString(1);
                tx[i].setText(bookName);
                tx[i].setLayoutX(100);
                tx[i].setLayoutY(i * 100 + 100);
                tx[i].setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {

                        OracleJDBC ojdbc = new OracleJDBC();
                        ojdbc.createConnection();
                        ResultSet rset1, rset2, rset3, rset4;
                        rset1 = ojdbc.retrieveBookInfo(bookName);
                        rset2 = ojdbc.retrieveAuthorName(bookName);
                        rset3 = ojdbc.retrievePublisherName(bookName);
                        rset4 = ojdbc.retrievePrequel(bookName);
                        BookInformation info = new BookInformation(primaryStage, scene, "MEMBERS", rset1, rset2, rset3,
                                rset4);
                        info.sceneshow();
                    }
                });
                vbox.getChildren().add(tx[i]);
                i++;
                System.out.println("i = " + rs.getString(1));
                // t.setText(rs.getString("Email"));
            }

            Button viewBooks = new Button();
            viewBooks.setText("View All Books");
            viewBooks.setLayoutX(100);
            viewBooks.setLayoutY(200);

            // vbox.getChildren().addAll( viewBooks , viewprofle);

            Button back = new Button();
            back.setLayoutX(500);
            back.setLayoutY(500);
            back.setText("Back");
            back.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    LoginPage loginpage = new LoginPage(primaryStage);
                    loginpage.sceneshow(prevscene);

                }
            });
            root.getChildren().addAll(back);

            // Set the viewport width and height.
            sp.setPrefViewportWidth(700);
            sp.setPrefViewportHeight(450);
            // Enable panning.
            sp.setPannable(true);
            // VBox.setVgrow(sp, Priority.ALWAYS);
            sp.setContent(vbox);
            root.getChildren().addAll(sp);

        } catch (SQLException ex) {
            Logger.getLogger(BookList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scene;
    }

    void sceneshow() {

        primaryStage.setScene(this.getscene());

    }

}
