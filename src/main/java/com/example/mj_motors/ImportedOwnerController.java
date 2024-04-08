package com.example.mj_motors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ImportedOwnerController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TableView<OwnerRecord> importedRecord;
    @FXML
    TableColumn<OwnerRecord, Integer> newCarId;
    @FXML
    TableColumn<OwnerRecord, Integer> oldCarId;
    @FXML
    TableColumn<OwnerRecord, Integer> manId;
    @FXML
    TableColumn<OwnerRecord, Integer> custId;
    @FXML
    TableColumn<OwnerRecord, String> carName;
    @FXML
    TableColumn<OwnerRecord, String> carType;
    @FXML
    TableColumn<OwnerRecord, Integer> carCondition;
    @FXML
    TableColumn<OwnerRecord, Integer> carModel;
    @FXML
    TableColumn<OwnerRecord, Integer> carPrice;
    @FXML
    TableColumn<OwnerRecord, Integer> carImportYear;
    @FXML
    TableColumn<OwnerRecord, String> carAuction;
    @FXML
    TableColumn<OwnerRecord, Integer> carGrade;

    ObservableList<OwnerRecord> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery(" select * from importedpurchases natural JOIN (select car_id , car_name , car_price , car_type , car_condition , car_model , car_yearofimport ,car_auctionsheet , car_grade from newimportedcars UNION SELECT car_id , car_name , car_price , car_type , car_condition , car_model , car_yearofimport ,car_auctionsheet , car_grade from oldimportedcars)");
            while (rs.next()) {
                list.add(new OwnerRecord(rs.getInt("CUST_ID"), rs.getInt("MAN_ID")
                        , rs.getInt("NEWCAR_ID"), rs.getInt("OLDCAR_ID"), rs.getString("CAR_NAME"),rs.getString("CAR_TYPE"),
                        rs.getInt("CAR_CONDITION") ,rs.getInt("CAR_MODEL") , rs.getInt("CAR_PRICE") , rs.getInt("CAR_YEAROFIMPORT") , rs.getString("CAR_AUCTIONSHEET") , rs.getInt("CAR_GRADE")));
            }

            newCarId.setCellValueFactory(new PropertyValueFactory<>("newCarId"));
            oldCarId.setCellValueFactory(new PropertyValueFactory<>("oldCarId"));
            carName.setCellValueFactory(new PropertyValueFactory<>("carName"));
            carPrice.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
            carModel.setCellValueFactory(new PropertyValueFactory<>("carModel"));
            carCondition.setCellValueFactory(new PropertyValueFactory<>("carCondition"));
            carType.setCellValueFactory(new PropertyValueFactory<>("carType"));
            carImportYear.setCellValueFactory(new PropertyValueFactory<>("carImportYear"));
            carAuction.setCellValueFactory(new PropertyValueFactory<>("carAuction"));
            carGrade.setCellValueFactory(new PropertyValueFactory<>("carGrade"));
            custId.setCellValueFactory(new PropertyValueFactory<>("custId"));
            manId.setCellValueFactory(new PropertyValueFactory<>("manId"));

            importedRecord.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void checkAllStock_2(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("checkStockOwn2.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
