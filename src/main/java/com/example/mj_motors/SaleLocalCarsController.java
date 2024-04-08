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

public class SaleLocalCarsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane pane1, pane2;

    @FXML
    private Spinner<Integer> spnCondition, spnModel;

    @FXML
    private ChoiceBox<String> chBoxType;
    @FXML
    public TextField carName, userPrice;
    @FXML
    public Label carPrice;
    public int cust_ID;

    private String[] carTypes = {"HatchBack", "Sedan", "Crossover", "SUV"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10,1);
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
    protected void goBack(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("localCarsSection.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("localSection.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void sellCar(ActionEvent actionEvent) throws IOException {
        pane1.setVisible(false);
        pane2.setVisible(true);
        int carModel = parseInt(spnModel.getValue().toString());
        int carCondition = parseInt(spnCondition.getValue().toString());
        String carType = chBoxType.getValue();

        if (!carName.getText().isEmpty() && !chBoxType.getSelectionModel().isEmpty()) {

            if(carType.equalsIgnoreCase("HATCHBACK") && carCondition >= 7 && carModel >= 2015){
                carPrice.setText("1500000 - 1800000");
            }
            else if(carType.equalsIgnoreCase("HATCHBACK") && carCondition >= 5 && carModel >= 2012){
                carPrice.setText("1000000 - 1500000");
            }
            else if(carType.equalsIgnoreCase("HATCHBACK") && carCondition >= 1 && carModel >= 2008){
                carPrice.setText("800000 - 1300000");
            }
            else if(carType.equalsIgnoreCase("SEDAN") && carCondition >= 7 && carModel >= 2015){
                carPrice.setText("2800000 - 3300000");
            }
            else if(carType.equalsIgnoreCase("SEDAN") && carCondition >= 5 && carModel >= 2012){
                carPrice.setText("1700000 - 2000000");
            }
            else if(carType.equalsIgnoreCase("SEDAN") && carCondition >= 1 && carModel >= 2008){
                carPrice.setText("1200000 - 1600000");
            }
            else if(carType.equalsIgnoreCase("CROSSOVER") && carCondition >= 7 && carModel >= 2015){
                carPrice.setText("4600000 - 5300000");
            }
            else if(carType.equalsIgnoreCase("CROSSOVER") && carCondition >= 5 && carModel >= 2012){
                carPrice.setText("3800000 - 4300000");
            }
            else if(carType.equalsIgnoreCase("CROSSOVER") && carCondition >= 1 && carModel >= 2008){
                carPrice.setText("3100000 - 3600000");
            }
            else if(carType.equalsIgnoreCase("SUV") && carCondition >= 7 && carModel >= 2015){
                carPrice.setText("6800000 - 7300000");
            }
            else if(carType.equalsIgnoreCase("SUV") && carCondition >= 5 && carModel >= 2012){
                carPrice.setText("6000000 - 6500000");
            }
            else if(carType.equalsIgnoreCase("SUV") && carCondition >= 1 && carModel >= 2008){
                carPrice.setText("5400000 - 5900000");
            }

        }
        else {
            pane1.setVisible(true);
            pane2.setVisible(false);
            carsEmptyCredentialsDialog();
        }


    }

    @FXML
    protected void cancel(ActionEvent actionEvent) throws IOException {
        pane1.setVisible(true);
        pane2.setVisible(false);

    }
    @FXML
    protected void saleLocalCar(ActionEvent actionEvent) throws IOException, SQLException {
        int carId = (int) (Math.random() * 2000) + 1;

        Db_Connection regConn = new Db_Connection();
        if(userPrice.getText().isEmpty()){
            carsEmptyCredentialsDialog();
        }
        else {
            if( chBoxType.getValue().equalsIgnoreCase("HATCHBACK") && parseInt(spnCondition.getValue().toString()) >= 7 && parseInt(spnCondition.getValue().toString()) <= 10
                    && parseInt(spnModel.getValue().toString()) >= 2015 && parseInt(spnModel.getValue().toString()) <= 2022
                    && parseInt(userPrice.getText()) >= 1500000 && parseInt(userPrice.getText()) <= 1800000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
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
            else if( chBoxType.getValue().equalsIgnoreCase("HATCHBACK") && parseInt(spnCondition.getValue().toString()) >= 5 && parseInt(spnCondition.getValue().toString()) < 7
                    && parseInt(spnModel.getValue().toString()) >= 2012 && parseInt(spnModel.getValue().toString()) < 2015
                    && parseInt(userPrice.getText()) >= 100000 && parseInt(userPrice.getText()) <= 1500000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
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
            else if(chBoxType.getValue().equalsIgnoreCase("HATCHBACK") && parseInt(spnCondition.getValue().toString()) >= 1 && parseInt(spnCondition.getValue().toString()) < 5
                    && parseInt(spnModel.getValue().toString()) >= 2008 && parseInt(spnModel.getValue().toString()) < 2012
                    && parseInt(userPrice.getText()) > 800000 && parseInt(userPrice.getText()) < 1300000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
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
            else if(chBoxType.getValue().equalsIgnoreCase("SEDAN") && parseInt(spnCondition.getValue().toString()) >= 7 && parseInt(spnCondition.getValue().toString()) <= 10
                    && parseInt(spnModel.getValue().toString()) >= 2015 && parseInt(spnModel.getValue().toString()) < 2022
                    && parseInt(userPrice.getText()) >= 2800000 && parseInt(userPrice.getText()) <= 3300000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
                String sql2 = sqlTbl1 + sqlVal1;
                Statement statement2= connection.createStatement();
                int rows1 = statement2.executeUpdate(sql2);
                if (rows1 > 0) {
                    System.out.println(" cars Inserted into LOCALNEWCUSTOMERPURCHASE successfully");
                    statement2.close();

                    // connection.close();

                }
                carAddSuccessDialog();
                carName.clear();
            }
            else if(chBoxType.getValue().equalsIgnoreCase("SEDAN") && parseInt(spnCondition.getValue().toString()) >= 5 && parseInt(spnCondition.getValue().toString()) < 7
                    && parseInt(spnModel.getValue().toString()) >= 2012 && parseInt(spnModel.getValue().toString()) < 2015
                    && parseInt(userPrice.getText()) > 1700000 && parseInt(userPrice.getText()) < 2000000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
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
            else if(chBoxType.getValue().equalsIgnoreCase("SEDAN") && parseInt(spnCondition.getValue().toString()) >= 1 && parseInt(spnCondition.getValue().toString()) < 5
                    && parseInt(spnModel.getValue().toString()) >= 2008 && parseInt(spnModel.getValue().toString()) < 2012
                    && parseInt(userPrice.getText()) > 1200000 && parseInt(userPrice.getText()) < 1600000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
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
            else if(chBoxType.getValue().equalsIgnoreCase("CROSSOVER") && parseInt(spnCondition.getValue().toString()) >= 7 && parseInt(spnCondition.getValue().toString()) <= 10
                    && parseInt(spnModel.getValue().toString()) >= 2015 && parseInt(spnModel.getValue().toString()) < 2022
                    && parseInt(userPrice.getText()) > 4600000 && parseInt(userPrice.getText())< 5300000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
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
            else if(chBoxType.getValue().equalsIgnoreCase("CROSSOVER") && parseInt(spnCondition.getValue().toString()) >= 5 && parseInt(spnCondition.getValue().toString()) < 7
                    && parseInt(spnModel.getValue().toString()) >= 2012 && parseInt(spnModel.getValue().toString()) < 2015
                    && parseInt(userPrice.getText()) > 3800000 && parseInt(userPrice.getText()) < 4300000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
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
            else if(chBoxType.getValue().equalsIgnoreCase("CROSSOVER") && parseInt(spnCondition.getValue().toString()) >= 1 && parseInt(spnCondition.getValue().toString()) < 5
                    && parseInt(spnModel.getValue().toString()) >= 2008 && parseInt(spnModel.getValue().toString()) < 2012
                    && parseInt(userPrice.getText()) > 3100000 && parseInt(userPrice.getText()) < 3600000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
                String sql2 = sqlTbl1 + sqlVal1;
                Statement statement2= connection.createStatement();
                int rows1 = statement2.executeUpdate(sql2);
                if (rows1 > 0) {
                    System.out.println(" cars Inserted into LOCALNEWCUSTOMERPURCHASE successfully");
                    statement2.close();

                    // connection.close();

                }
                carAddSuccessDialog();
                carName.clear();

            }
            else if(chBoxType.getValue().equalsIgnoreCase("SUV") && parseInt(spnCondition.getValue().toString()) >= 7 && parseInt(spnCondition.getValue().toString()) <= 10
                    && parseInt(spnModel.getValue().toString()) >= 2015 && parseInt(spnModel.getValue().toString()) < 2022
                    && parseInt(userPrice.getText()) > 6800000 && parseInt(userPrice.getText()) < 7300000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
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
            else if(chBoxType.getValue().equalsIgnoreCase("SUV") && parseInt(spnCondition.getValue().toString()) >= 5 && parseInt(spnCondition.getValue().toString()) < 7
                    && parseInt(spnModel.getValue().toString()) >= 2012 && parseInt(spnModel.getValue().toString()) < 2015
                    && parseInt(userPrice.getText()) > 6000000 && parseInt(userPrice.getText())< 6500000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
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
            else if(chBoxType.getValue().equalsIgnoreCase("SUV") && parseInt(spnCondition.getValue().toString()) >= 1 && parseInt(spnCondition.getValue().toString()) < 5
                    && parseInt(spnModel.getValue().toString()) >= 2008 && parseInt(spnModel.getValue().toString()) < 2012
                    && parseInt(userPrice.getText()) > 5400000 && parseInt(userPrice.getText()) < 5900000){
                regConn.localCarSale( carId, carName.getText(), parseInt(userPrice.getText()),parseInt(spnModel.getValue().toString()),parseInt(spnCondition.getValue().toString())  , chBoxType.getValue());
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO LOCALSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 11 + "', '" + carId + "')";
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
            else {
                carsBargainDialog();
            }

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

    protected void carsBargainDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("We Cannot buy this Car in this price");
            userPrice.clear();
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
