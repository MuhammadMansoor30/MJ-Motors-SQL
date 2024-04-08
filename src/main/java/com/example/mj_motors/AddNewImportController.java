package com.example.mj_motors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class AddNewImportController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Spinner<Integer> spnModel, spnCondition, spnCarGrade, spnYearOfImport;

    @FXML
    private ChoiceBox<String> chBoxType, chBoxAuctionSht;
    @FXML
    private TextField carNameAdd, carPriceAdd;

    private String[] carTypes = {"HatchBack", "Sedan", "Crossover", "SUV", "Sports"};

    private String[] options = {"Yes! Available", "Not Available"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 10, 1);
        spnCondition.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2014, 2021, 1);
        spnModel.setValueFactory(valueFactory1);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2016, 2022, 1);
        spnYearOfImport.setValueFactory(valueFactory2);

        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
        spnCarGrade.setValueFactory(valueFactory3);

        chBoxType.getItems().addAll(carTypes);

        chBoxAuctionSht.getItems().addAll(options);
    }

    @FXML
    protected void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("manLoginImported.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("man.css");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    protected void addNewImportedCar(ActionEvent actionEvent) throws IOException, SQLException {
        String carName = carNameAdd.getText();
        String carPrice = carPriceAdd.getText();
        String carModel = spnModel.getValue().toString();
        String carCondition = spnCondition.getValue().toString();
        String carAuctionSheet = chBoxAuctionSht.getValue();
        String carGrade = spnCarGrade.getValue().toString();
        String carType = chBoxType.getValue();
        String carYearOfImport = spnYearOfImport.getValue().toString();
        int carId = (int) (Math.random() * 1000) + 1;

        Db_Connection regConn = new Db_Connection();
        if (carName.isEmpty() || carPrice.isEmpty() || chBoxType.getSelectionModel().isEmpty() || chBoxAuctionSht.getSelectionModel().isEmpty()) {
            carsEmptyCredentialsDialog();
        } else {

            if (checkInt(carPriceAdd)) {
                regConn.addManagerImportedCarsToDb(1 , carId, carName, parseInt(carPrice), parseInt(carModel), parseInt(carCondition), carType,
                parseInt(carYearOfImport), carAuctionSheet, parseInt(carGrade));
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO ADDIMPORTED (MAN_ID , NEWCAR_ID)";
                String sqlVal1 = "VALUES ('" + 21 + "', '" + carId + "')";
                String sql2 = sqlTbl1 + sqlVal1;
                Statement statement2= connection.createStatement();
                int rows1 = statement2.executeUpdate(sql2);
                if (rows1 > 0) {
                    statement2.close();

                    // connection.close();

                }
                carAddSuccessDialog();
                carNameAdd.clear();
                carPriceAdd.clear();


            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Caution!");
                    alert.setContentText("Oops ! Looks like some entered Values are not a number. Please Try Again ");
                    alert.showAndWait();
                });


                carNameAdd.clear();
                carPriceAdd.clear();


            }
        }


    }

    @FXML
    protected void carsEmptyCredentialsDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("Please fill the required Credentials");
            alert.showAndWait();
        });

    }

    @FXML
    private boolean checkInt(TextField carPrice) {
        try {
            Integer.parseInt(carPrice.getText());
            return true;
        } catch (NumberFormatException e) {

            return false;
        }
    }

    @FXML
    protected void carAddSuccessDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setContentText("CAR HAS BEEN ADDED SUCCESSFULLY ");
            alert.showAndWait();
        });

    }
}
