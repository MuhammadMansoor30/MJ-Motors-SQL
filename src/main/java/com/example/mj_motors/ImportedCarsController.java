package com.example.mj_motors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ImportedCarsController {
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
    protected void backToImported(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("importedCarsSection.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("importedSection.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void purchaseImportedCars(ActionEvent actionEvent) throws IOException{
        root = FXMLLoader.load(getClass().getResource("purchaseMenuImported.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("purchaseMenu.css");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void newCarsSection(ActionEvent actionEvent) throws IOException{
        root = FXMLLoader.load(getClass().getResource("newImportedCarPur.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void oldCarsSectionController(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("oldImportedCarPur.fxml"));
        root = loader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void saleImportedCarsController(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("importedCarsSale.fxml"));
        root = loader.load();
        //LocalCarsController localCarsController = loader.getController();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("saleImported.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void placeCarForSaleController(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("placeSaleImported.fxml"));
        root = loader.load();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("placeImported.css");
        stage.setScene(scene);
        stage.show();
    }
}
