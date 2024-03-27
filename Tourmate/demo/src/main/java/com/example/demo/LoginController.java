package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController extends Application{
    @FXML
    private static Button exitApplication;
    @FXML
    private Button signInButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label failedLoginMessage;
    @FXML
    private Hyperlink signUpLink;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    @FXML
    private void exitApplication(ActionEvent event){
        System.exit(0);
    }
    @FXML
    private void login(ActionEvent event) throws IOException {
        if(usernameField.getText().isBlank() == false && passwordField.getText().isBlank() == false){
            boolean token = validateLogin(); // checks DB for valid credentials
            if (token) { // Proceeds to Profile screen
                Parent root = FXMLLoader.load(Application.class.getResource("Profile_view.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                root.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                        xOffset = mouseEvent.getSceneX();
                        yOffset = mouseEvent.getSceneY();
                    }
                });
                root.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stage.setX(mouseEvent.getScreenX() - xOffset);
                        stage.setY(mouseEvent.getScreenY() - yOffset);
                    }
                });
                stage.setTitle("Tourmate");
                stage.setScene(scene);
                stage.show();
            } else {
                failedLoginMessage.setText("Invalid Login. Please try again.");
            }
        } else{
            failedLoginMessage.setText("Please enter an username and password.");
        }
    }
    @FXML
    private void registerAccount(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Application.class.getResource("Reg_view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        root.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });
        stage.setTitle("Tourmate");
        stage.setScene(scene);
        stage.show();
    }
    private boolean validateLogin(){
        DatabaseConnection connectnow = new DatabaseConnection();
        Connection connectDB = connectnow.getConnection();
        String verifyLogin = "SELECT COUNT(1) FROM registration WHERE UserName= '"+usernameField.getText() +"' AND Pass_word = '"+ passwordField.getText() +"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1) != 1){
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}