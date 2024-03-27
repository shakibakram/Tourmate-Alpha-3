package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileController extends Application implements Initializable {
    @FXML
    private static Button exitApplication;
    @FXML
    private static Button minimizeApplication;
    @FXML
    private static Button goToProfileView;
    @FXML
    private static Button editProfile;
    @FXML
    private static Button logOut;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    @FXML
    protected void setExitApplication(ActionEvent event){
        System.exit(0);
    }
    @FXML
    protected void setMinimizeApplication(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    protected void viewProfile(ActionEvent event) throws IOException{
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
    }
    @FXML
    protected void editProfile(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Profile_edit.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void logout(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Application.class.getResource("Login_view.fxml"));
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

    @FXML
    private Circle profileImageBorder;
    @FXML
    private Text name; // concatenate FirstName and LastName from DB
    @FXML
    private Text username;
    @FXML
    private Text email;

    @FXML
    private TextField searchBar;
    @FXML
    private ImageView profileIcon;
    @FXML
    private ImageView sidebarToggleOpen;
    @FXML
    private ImageView sidebarToggleClose;
    @FXML
    private AnchorPane sidebarAnchorPane;
    @FXML
    private AnchorPane profilePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Default profile avatar
        Image profileImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/media/avatar.png")));
        profileImageBorder.setFill(new ImagePattern(profileImage));

        // Sidebar toggling
        sidebarAnchorPane.setTranslateX(-163);
        sidebarToggleOpen.setOnMouseClicked(event -> {
            TranslateTransition tt1 = new TranslateTransition();
            tt1.setDuration(Duration.seconds(0.4));
            tt1.setNode(sidebarAnchorPane);

            tt1.setToX(0);
            tt1.play();

            sidebarAnchorPane.setTranslateX(-163);

            tt1.setOnFinished((ActionEvent eventAlpha) -> {
                sidebarToggleOpen.setVisible(false);
                sidebarToggleClose.setVisible(true);
            });
        });
        sidebarToggleClose.setOnMouseClicked(event -> {
            TranslateTransition tt2 = new TranslateTransition();
            tt2.setDuration(Duration.seconds(0.4));
            tt2.setNode(sidebarAnchorPane);

            tt2.setToX(-163);
            tt2.play();

            sidebarAnchorPane.setTranslateX(0);

            tt2.setOnFinished((ActionEvent eventBeta) -> {
                sidebarToggleOpen.setVisible(true);
                sidebarToggleClose.setVisible(false);
            });
        });

        // Icon Navigation
        profileIcon.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(Application.class.getResource("Profile_view.fxml"));
                stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                root.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
