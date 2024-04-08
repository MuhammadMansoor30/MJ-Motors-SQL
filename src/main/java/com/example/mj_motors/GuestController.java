package com.example.mj_motors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GuestController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    protected void backToGuest(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("guestMenu.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("styleMenu.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void backToGuestImp(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("guestMenu.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("styleMenu.css");
        stage.setScene(scene);
        stage.show();

    }



    @FXML
    protected void getRegistered(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("register.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void checkoutLocalCars(ActionEvent actionEvent) throws IOException{
        root = FXMLLoader.load(getClass().getResource("checkoutMenuLocal.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("localSection.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void checkoutImportedCars(ActionEvent actionEvent) throws IOException{
        root = FXMLLoader.load(getClass().getResource("checkoutMenuImported.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("importedSection.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void newCarsSectionGuestImp(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("checkoutNewImpCar.fxml"));
        root = loader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void oldCarsSectionGuestImp(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("checkoutOldImpCar.fxml"));
        root = loader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void newCarsSectionGuestLoc(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("checkoutNewLocalCar.fxml"));
        root = loader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void oldCarsSectionGuestLoc(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("checkoutOldLocalCar.fxml"));
        root = loader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void localCarsSectionGuest(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("localCarsSectionGuest.fxml"));
        root = loader.load();
        //LocalCarsController localCarsController = loader.getController();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("guest1.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void importedCarsSectionGuest(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("importedCarsSectionGuest.fxml"));
        root = loader.load();
        //LocalCarsController localCarsController = loader.getController();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("guest2.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void exit() {
        Platform.exit();
    }


}
