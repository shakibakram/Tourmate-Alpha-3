package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
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
    private void login(ActionEvent event) throws IOException {
        if(usernameField.getText().isBlank() == false && passwordField.getText().isBlank() == false){
            boolean token = validateLogin(); // checks DB for valid credentials
            if (token) { // Proceeds to Profile screen
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Profile_view.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(fxmlLoader.load());
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
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Reg_view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
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