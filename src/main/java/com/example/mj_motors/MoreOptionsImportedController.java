package com.example.mj_motors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoreOptionsImportedController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TableView<OwnerOptions> optionsTable;

    @FXML
    TableColumn<OwnerOptions, Integer> carId;
    @FXML
    TableColumn<OwnerOptions, String> carName;

    @FXML
    TableColumn<OwnerOptions, Integer> carPrice;

    @FXML
    TableColumn<OwnerOptions, Integer> manId;

    @FXML
    TableColumn<OwnerOptions, String> manName;

    @FXML
    TableColumn<OwnerOptions, Integer> manPhNo;
    @FXML
    TableColumn<OwnerOptions, Integer> custId;


    ObservableList<OwnerOptions> customerNotBuyCarList = FXCollections.observableArrayList();
    ObservableList<OwnerOptions> customerBuyMoreThan1List = FXCollections.observableArrayList();
    ObservableList<OwnerOptions> carCommissionList = FXCollections.observableArrayList();
    ObservableList<OwnerOptions> customerSoldCarList = FXCollections.observableArrayList();
    ObservableList<OwnerOptions> managerSoldCarList = FXCollections.observableArrayList();

    @FXML
    protected void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("checkStockOwn2.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void customerNotBuyCarCheck(ActionEvent actionEvent) throws IOException {
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT CUST_ID FROM CUSTOMER MINUS SELECT CUST_ID FROM IMPORTEDPURCHASES");
            while (rs.next()) {
                customerNotBuyCarList.add(new OwnerOptions(rs.getInt("CUST_ID")));
            }

            custId.setCellValueFactory(new PropertyValueFactory<>("custId"));

            customerBuyMoreThan1List = FXCollections.observableArrayList();
            carCommissionList = FXCollections.observableArrayList();
            customerSoldCarList = FXCollections.observableArrayList();
            managerSoldCarList = FXCollections.observableArrayList();


            optionsTable.setItems(customerNotBuyCarList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void customerBuyMoreThan1(ActionEvent actionEvent) throws IOException {
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT CUST_ID FROM CUSTOMER WHERE CUST_ID IN (SELECT CUST_ID FROM IMPORTEDPURCHASES GROUP BY CUST_ID HAVING COUNT(CUST_ID) > 1)");
            while (rs.next()) {
                customerBuyMoreThan1List.add(new OwnerOptions(rs.getInt("CUST_ID")));
            }

            custId.setCellValueFactory(new PropertyValueFactory<>("custId"));


            customerNotBuyCarList = FXCollections.observableArrayList();
            carCommissionList = FXCollections.observableArrayList();
            customerSoldCarList = FXCollections.observableArrayList();
            managerSoldCarList = FXCollections.observableArrayList();


            optionsTable.setItems(customerBuyMoreThan1List);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void carCommission(ActionEvent actionEvent) throws IOException {
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT CAR_ID, CAR_NAME FROM IMPORTEDCOMMISSION WHERE IMPORTED_COMM > 20000");
            while (rs.next()) {
                carCommissionList.add(new OwnerOptions(rs.getInt("CAR_ID"), rs.getString("CAR_NAME")));
            }

            carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
            carName.setCellValueFactory(new PropertyValueFactory<>("carName"));

            customerNotBuyCarList = FXCollections.observableArrayList();
            customerBuyMoreThan1List = FXCollections.observableArrayList();
            customerSoldCarList = FXCollections.observableArrayList();
            managerSoldCarList = FXCollections.observableArrayList();

            optionsTable.setItems(carCommissionList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void customerSoldCar(ActionEvent actionEvent) throws IOException {
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT CAR_NAME, CAR_PRICE FROM IMPORTEDCUSTOMERSALE WHERE CAR_TYPE IN (SELECT CAR_TYPE FROM IMPORTEDCUSTOMERSALE GROUP BY CAR_TYPE HAVING CAR_TYPE = 'Sedan') ORDER BY CAR_NAME");
            while (rs.next()) {
                customerSoldCarList.add(new OwnerOptions(rs.getString("CAR_NAME"), rs.getInt("CAR_PRICE")));
            }

            carName.setCellValueFactory(new PropertyValueFactory<>("carName"));
            carPrice.setCellValueFactory(new PropertyValueFactory<>("carPrice"));

            customerNotBuyCarList = FXCollections.observableArrayList();
            customerBuyMoreThan1List = FXCollections.observableArrayList();
            carCommissionList = FXCollections.observableArrayList();
            managerSoldCarList = FXCollections.observableArrayList();


            optionsTable.setItems(customerSoldCarList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void managerSoldCar(ActionEvent actionEvent) throws IOException {
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT MAN_ID, MAN_NAME , MAN_PHNUMBER FROM MANAGERS WHERE MAN_ID IN (SELECT MAN_ID FROM IMPORTEDPURCHASES GROUP BY MAN_ID HAVING COUNT (MAN_ID)>5)");
            while (rs.next()) {
                managerSoldCarList.add(new OwnerOptions(rs.getInt("MAN_ID"), rs.getString("MAN_NAME"), rs.getInt("MAN_PHNUMBER")));

            }

            manId.setCellValueFactory(new PropertyValueFactory<>("manId"));
            manName.setCellValueFactory(new PropertyValueFactory<>("manName"));
            manPhNo.setCellValueFactory(new PropertyValueFactory<>("manPhNo"));

            customerNotBuyCarList = FXCollections.observableArrayList();
            customerBuyMoreThan1List = FXCollections.observableArrayList();
            carCommissionList = FXCollections.observableArrayList();
            customerSoldCarList = FXCollections.observableArrayList();

            optionsTable.setItems(managerSoldCarList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
