package com.example.mj_motors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LocalCarsController{
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    AnchorPane pane1, pane2;


    @FXML
    protected void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("styleMenu.css");
        stage.setScene(scene);
        stage.show();

    }



    @FXML
    protected void backToLocal(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("localCarsSection.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("localSection.css");
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    protected void purchaseLocalCars(ActionEvent actionEvent) throws IOException{
        root = FXMLLoader.load(getClass().getResource("purchaseMenuLocal.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("purchaseMenu.css");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void newCarsSection(ActionEvent actionEvent) throws IOException{
        root = FXMLLoader.load(getClass().getResource("newLocalCarsPur.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void oldCarsSectionController(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("oldLocalCarsPur.fxml"));
        root = loader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void saleLocalCarsController(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("localCarsSale.fxml"));
        root = loader.load();
        //LocalCarsController localCarsController = loader.getController();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("saleLocal.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void placeCarForSaleController(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("placeSaleLocal.fxml"));
        root = loader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("localPlace.css");
        stage.setScene(scene);
        stage.show();
    }

}
