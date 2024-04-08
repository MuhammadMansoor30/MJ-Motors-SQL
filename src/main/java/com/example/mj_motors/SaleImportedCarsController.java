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

public class SaleImportedCarsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane pane1, pane2;

    @FXML
    private Spinner<Integer> spnCondition, spnModel, spnGrade, spnYearImport;

    @FXML
    private ChoiceBox<String> chBoxType, chBoxAuction;

    @FXML
    private TextField carName, userPrice;

    @FXML
    private Label carPrice;

    private String[] carTypes = {"HatchBack", "Sedan", "Crossover", "SUV", "Sports"};

    private String[] values = {"Yes! Available", "NoT Available"};
    public int cust_ID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10,1);
        spnCondition.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2008, 2020, 1);
        spnModel.setValueFactory(valueFactory1);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
        spnGrade.setValueFactory(valueFactory2);

        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2010, 2022, 1);
        spnYearImport.setValueFactory(valueFactory3);

        chBoxType.getItems().addAll(carTypes);

        chBoxAuction.getItems().addAll(values);

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
        root = FXMLLoader.load(getClass().getResource("importedCarsSection.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("importedSection.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void sellCar(ActionEvent actionEvent) throws IOException {
        pane1.setVisible(false);
        pane2.setVisible(true);
        int carModel = parseInt(spnModel.getValue().toString());
        int carCondition = parseInt(spnCondition.getValue().toString());
        int carGrade = parseInt(spnGrade.getValue().toString());
        String carType = chBoxType.getValue();
        int carYearOfImport = parseInt(spnYearImport.getValue().toString());

        if(carType.equalsIgnoreCase("HATCHBACK") && carCondition >= 7 && carModel >= 2015
           && carYearOfImport >= 2017 && carGrade >= 4){
            carPrice.setText("1700000 - 2000000");
        }
        else if(carType.equalsIgnoreCase("HATCHBACK") && carCondition >= 5 && carModel >= 2012
                && carYearOfImport >= 2013 && carGrade >= 3){
            carPrice.setText("1200000 - 1700000");
        }
        else if(carType.equalsIgnoreCase("HATCHBACK") && carCondition >= 1 && carModel >= 2008
                && carYearOfImport >= 2010 && carGrade >= 2){
            carPrice.setText("900000 - 1500000");
        }
        else if(carType.equalsIgnoreCase("SEDAN") && carCondition >= 7 && carModel >= 2015
                && carYearOfImport >= 2017 && carGrade >= 4){
            carPrice.setText("3000000 - 3500000");
        }
        else if(carType.equalsIgnoreCase("SEDAN") && carCondition >= 5 && carModel >= 2012
                && carYearOfImport >= 2013 && carGrade >= 3){
            carPrice.setText("2300000 - 2700000");
        }
        else if(carType.equalsIgnoreCase("SEDAN") && carCondition >= 1 && carModel >= 2008
                && carYearOfImport >= 2010 && carGrade >= 2){
            carPrice.setText("1700000 - 2000000");
        }
        else if(carType.equalsIgnoreCase("CROSSOVER") && carCondition >= 7 && carModel >= 2015
                && carYearOfImport >= 2017 && carGrade >= 4){
            carPrice.setText("4800000 - 5500000");
        }
        else if(carType.equalsIgnoreCase("CROSSOVER") && carCondition >= 5 && carModel >= 2012
                && carYearOfImport >= 2013 && carGrade >= 3){
            carPrice.setText("4200000 - 4600000");
        }
        else if(carType.equalsIgnoreCase("CROSSOVER") && carCondition >= 1 && carModel >= 2008
                && carYearOfImport >= 2010 && carGrade >= 2){
            carPrice.setText("3700000 - 4000000");
        }
        else if(carType.equalsIgnoreCase("SUV") && carCondition >= 7 && carModel >= 2015
                && carYearOfImport >= 2017 && carGrade >= 4){
            carPrice.setText("7000000 - 7500000");
        }
        else if(carType.equalsIgnoreCase("SUV") && carCondition >= 5 && carModel >= 2012
                && carYearOfImport >= 2013 && carGrade >= 3){
            carPrice.setText("6200000 - 6700000");
        }
        else if(carType.equalsIgnoreCase("SUV") && carCondition >= 1 && carModel >= 2008
                && carYearOfImport >= 2010 && carGrade >= 2){
            carPrice.setText("5700000 - 6100000");
        }
        else if(carType.equalsIgnoreCase("SPORTS") && carCondition >= 7 && carModel >= 2015
                && carYearOfImport >= 2017 && carGrade >= 4){
            carPrice.setText("11000000 - 12500000");
        }
        else if(carType.equalsIgnoreCase("SPORTS") && carCondition >= 5 && carModel >= 2012
                && carYearOfImport >= 2013 && carGrade >= 3){
            carPrice.setText("10500000 - 11500000");
        }
        else if(carType.equalsIgnoreCase("SPORTS") && carCondition >= 1 && carModel >= 2008
                && carYearOfImport >= 2010 && carGrade >= 2){
            carPrice.setText("9000000 - 11000000");
        }




    }

    @FXML
    protected void cancel(ActionEvent actionEvent) throws IOException {
        pane1.setVisible(true);
        pane2.setVisible(false);

    }
    @FXML
    protected void saleImportedCar(ActionEvent actionEvent) throws IOException, SQLException {
        String name = carName.getText();
        int usrPrice = parseInt(userPrice.getText());
        int carModel = parseInt(spnModel.getValue().toString());
        int carCondition = parseInt(spnCondition.getValue().toString());
        String carAuctionSheet = chBoxAuction.getValue();
        int carGrade = parseInt(spnGrade.getValue().toString());
        String carType = chBoxType.getValue();
        int carYearOfImport = parseInt(spnYearImport.getValue().toString());
        int carId = (int) (Math.random() * 2000) + 1;

        Db_Connection regConn = new Db_Connection();
        if (name.isEmpty() && chBoxType.getSelectionModel().isEmpty() && chBoxAuction.getSelectionModel().isEmpty()) {
            carsEmptyCredentialsDialog();
           }
        else if(carType.equalsIgnoreCase("HATCHBACK") && carCondition >= 7 && carCondition <=10 && carModel >= 2015
                && carModel <= 2020 && carYearOfImport >= 2017 && carYearOfImport <= 2022
                && carGrade >= 4 && usrPrice >= 1700000 && usrPrice <= 2000000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("HATCHBACK") && carCondition >= 5 && carCondition < 7
                && carModel >= 2012 && carModel < 2015 && carYearOfImport >= 2013 && carYearOfImport < 2017
                && carGrade >= 3 && usrPrice >= 900000 && usrPrice < 1500000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("HATCHBACK") && carCondition >= 1 && carCondition < 5
                && carModel >= 2008 && carModel < 2012 && carYearOfImport >= 2010 && carYearOfImport < 2013
                && carGrade >= 2 && usrPrice > 900000 && usrPrice < 1500000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("SEDAN") && carCondition >= 7 && carCondition < 10
                && carModel >= 2015 && carModel < 2020 && carYearOfImport >= 2017 && carYearOfImport <= 2022
                && carGrade >= 4 && usrPrice >= 3000000 && usrPrice <= 3500000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
            String sql2 = sqlTbl1 + sqlVal1;
            Statement statement2= connection.createStatement();
            int rows1 = statement2.executeUpdate(sql2);
            if (rows1 > 0) {
                statement2.close();
                //connection.close();
            }
            carAddSuccessDialog();
            carName.clear();
        }
        else if(carType.equalsIgnoreCase("SEDAN") && carCondition >= 5 && carCondition < 7
                && carModel >= 2012 && carModel < 2015 && carYearOfImport >= 2013 && carYearOfImport < 2017
                && carGrade >= 3 && usrPrice > 2300000 && usrPrice < 2700000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("SEDAN") && carCondition >= 1 && carCondition < 5
                && carModel >= 2008 && carModel < 2012 && carYearOfImport >= 2010 && carYearOfImport < 2013
                && carGrade >= 2 && usrPrice > 1700000 && usrPrice < 2000000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("CROSSOVER") && carCondition >= 7 && carCondition < 10
                && carModel >= 2015 && carModel < 2020 && carYearOfImport >= 2017 && carYearOfImport <= 2022
                && carGrade >= 4 && usrPrice > 4800000 && usrPrice < 5500000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("CROSSOVER") && carCondition >= 5 && carCondition < 7
                && carModel >= 2012 && carModel < 2015 && carYearOfImport >= 2013 && carYearOfImport < 2017
                && carGrade >= 3 && usrPrice > 4200000 && usrPrice < 4600000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("CROSSOVER") && carCondition >= 1 && carCondition < 5
                && carModel >= 2008 && carModel < 2012 && carYearOfImport >= 2010 && carYearOfImport < 2013
                && carGrade >= 2 && usrPrice > 3700000 && usrPrice < 4000000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("SUV") && carCondition >= 7 && carCondition < 10
                && carModel >= 2015 && carModel < 2020 && carYearOfImport >= 2017 && carYearOfImport <= 2022
                && carGrade >= 4 && usrPrice > 7000000 && usrPrice < 7500000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("SUV") && carCondition >= 5 && carCondition < 7
                && carModel >= 2012 && carModel < 2015 && carYearOfImport >= 2013 && carYearOfImport < 2017
                && carGrade >= 3 && usrPrice > 6200000 && usrPrice < 6700000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("SUV") && carCondition >= 1 && carCondition < 5
                && carModel >= 2008 && carModel < 2012 && carYearOfImport >= 2010 && carYearOfImport < 2013
                && carGrade >= 2 && usrPrice > 5700000 && usrPrice < 6100000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("SPORTS") && carCondition >= 7 && carCondition < 10
                && carModel >= 2015 && carModel < 2020 && carYearOfImport >= 2017 && carYearOfImport <= 2022
                && carGrade >= 4  && usrPrice > 11000000 && usrPrice < 12500000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("SPORTS") && carCondition >= 5 && carCondition < 7
                && carModel >= 2012 && carModel < 2015 && carYearOfImport >= 2013 && carYearOfImport < 2017
                && carGrade >= 3 && usrPrice > 10500000 && usrPrice < 11500000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
        else if(carType.equalsIgnoreCase("SPORTS") && carCondition >= 1 && carCondition < 5
                && carModel >= 2008 && carModel < 2012 && carYearOfImport >= 2010 && carYearOfImport < 2013
                && carGrade >= 2&& usrPrice > 9000000 && usrPrice < 11000000){
            regConn.importedCarSale(carId, name, usrPrice, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + carId + "')";
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
    protected void carsBargainDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("We Cannot buy this Car in this price");
            userPrice.clear();
            alert.showAndWait();
        });

    }


    @FXML
    protected void carAddSuccessDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setContentText("CAR HAS BEEN PLACED SUCCESSFULLY ");
            alert.showAndWait();
        });

    }

}
