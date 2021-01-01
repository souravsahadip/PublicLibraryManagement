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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author KIshore
 */
public class MemberHomePage {
    String email;
    Label title = new Label("Member HomePage");
    Pane root = new Pane();
    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    Scene prevscene;
    Stage primaryStage;

    public MemberHomePage(Stage primaryStage, Scene prevscene, String email) {
        this.primaryStage = primaryStage;
        this.prevscene = prevscene;
        this.email = email;
    }

    Scene getscene() {
        scene.setCursor(Cursor.DEFAULT);
        scene.setFill(Color.BLACK);

        Button viewprofle = new Button();
        viewprofle.setText("View Profile");
        viewprofle.setLayoutX(100);
        viewprofle.setLayoutY(150);
        Button viewBooks = new Button();
        viewBooks.setText("View All Books");
        viewBooks.setLayoutX(100);
        viewBooks.setLayoutY(200);

        root.getChildren().addAll(viewBooks, viewprofle);
        viewprofle.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                OracleJDBC ojdbc = new OracleJDBC();
                ojdbc.createConnection();
                ResultSet rset;
                rset = ojdbc.retrieveInfo("email", email, "MEMBERS");
                MemberInformation info = new MemberInformation(primaryStage, scene, "MEMBERS", rset);
                info.sceneshow();
            }
        });
        viewBooks.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {
                    OracleJDBC ojdbc = new OracleJDBC();
                    ojdbc.createConnection();
                    ResultSet rset1, rset2;
                    // rset = ojdbc.retrieveInfo(email, "LIBRARIAN" ) ;
                    rset1 = ojdbc.retrieveBookList();
                    System.out.println("retrieved bookList");
                    rset2 = ojdbc.retrieveBookCount();
                    rset2.next();
                    int bookCount = rset2.getInt(1);
                    System.out.println("retrieved bookcount;   " + bookCount);
                    BookList booklist = new BookList(primaryStage, scene, rset1, bookCount);
                    booklist.sceneshow();
                } catch (SQLException ex) {
                    Logger.getLogger(MemberHomePage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Button logout = new Button();
        logout.setLayoutX(500);
        logout.setLayoutY(500);
        logout.setText("logout");
        logout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                LoginPage loginpage = new LoginPage(primaryStage);
                loginpage.sceneshow();

            }
        });
        root.getChildren().addAll(logout);

        return scene;
    }

    void sceneshow() {

        primaryStage.setScene(this.getscene());

    }

}
