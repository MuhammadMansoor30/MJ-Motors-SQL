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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class PlaceCarSaleLocalController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane paneS1, paneS2;

    @FXML
    private Spinner<Integer> spnCondition, spnModel;

    @FXML
    private ChoiceBox<String> chBoxType;
    @FXML
    public TextField carName;
    @FXML
    public Label price;

    private String[] carTypes = {"HatchBack", "Sedan", "Crossover", "SUV"};
    public int cust_ID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        spnCondition.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2010, 2022, 1);
        spnModel.setValueFactory(valueFactory1);

        chBoxType.getItems().addAll(carTypes);
        int cust_id = 0;
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs1 = connection.createStatement().executeQuery("SELECT * FROM CUSTID");

            while (rs1.next()) {
                cust_id = rs1.getInt(1);
                cust_ID = cust_id;


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void placeSellCar(ActionEvent actionEvent) throws IOException {

        if ((carName.getText().isEmpty() || chBoxType.getValue().isEmpty()) || (!carName.getText().isEmpty() && chBoxType.getValue().isEmpty()) || (carName.getText().isEmpty() &&
                !chBoxType.getValue().isEmpty())) {
            paneS2.setVisible(false);
            carsEmptyCredentialsDialog();
        } else {
            if (chBoxType.getValue().equalsIgnoreCase("HATCHBACK")) {
                price.setText("20000");
                paneS1.setVisible(false);
                paneS2.setVisible(true);
            } else if (chBoxType.getValue().equalsIgnoreCase("SEDAN")) {
                price.setText("25000");

                paneS1.setVisible(false);
                paneS2.setVisible(true);
            } else if (chBoxType.getValue().equalsIgnoreCase("CROSSOVER")) {
                price.setText("30000");

                paneS1.setVisible(false);
                paneS2.setVisible(true);
            } else if (chBoxType.getValue().equalsIgnoreCase("SUV")) {
                price.setText("40000");

                paneS1.setVisible(false);
                paneS2.setVisible(true);
            } else if (chBoxType.getValue().equalsIgnoreCase("SPORTS")) {
                price.setText("50000");

                paneS1.setVisible(false);
                paneS2.setVisible(true);
            }

        }

    }

    @FXML
    protected void cancel_1(ActionEvent actionEvent) throws IOException {
        paneS1.setVisible(true);
        paneS2.setVisible(false);

    }

    @FXML
    protected void backToLocal(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("localCarsSection.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("localSection.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void placeLocalCar(ActionEvent actionEvent) throws IOException, SQLException {
        String name = carName.getText();
        int carModel = parseInt(spnModel.getValue().toString());
        int carCondition = parseInt(spnCondition.getValue().toString());
        String carType = chBoxType.getValue();
        int carCommission = parseInt(price.getText());

        int carId = (int) (Math.random() * 2000) + 1;

        Db_Connection regConn = new Db_Connection();
        if (name.isEmpty() && chBoxType.getSelectionModel().isEmpty()) {
            carsEmptyCredentialsDialog();
        } else {

            regConn.placeLocalCarSale(carId, name , carModel, carCondition, carType, carCommission);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , COMMCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 13 + "', '" + carId + "')";
            String sql2 = sqlTbl1 + sqlVal1;
            Statement statement2= connection.createStatement();
            int rows1 = statement2.executeUpdate(sql2);
            if (rows1 > 0) {
                statement2.close();

                // connection.close();

            }
            carAddSuccessDialog();
            carName.clear();
        }

    }

    protected void carsEmptyCredentialsDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("Please fill the required Credentials");
            alert.showAndWait();
        });

    }

    protected void carAddSuccessDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setContentText("CAR HAS BEEN PLACED SUCCESSFULLY ");
            alert.showAndWait();
        });

    }


}
