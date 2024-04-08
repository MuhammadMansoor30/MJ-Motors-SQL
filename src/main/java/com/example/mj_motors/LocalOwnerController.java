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

public class LocalOwnerController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TableView<OwnerRecord> localRecord;
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
    TableColumn<OwnerRecord, Integer> carCondition;
    @FXML
    TableColumn<OwnerRecord, Integer> carModel;

    ObservableList<OwnerRecord> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery(" SELECT * FROM LOCALPURCHASES NATURAL JOIN (SELECT CAR_ID , CAR_NAME , CAR_PRICE , CAR_TYPE , CAR_CONDITION , CAR_MODEL FROM NEWLOCALCARS UNION SELECT car_id , car_name , car_price , car_type , car_condition , car_model from oldlocalcars)");
            while (rs.next()) {
                System.out.println(rs.getString("Car_TYPE"));
                list.add(new OwnerRecord(rs.getInt("CUST_ID"), rs.getInt("MAN_ID")
                        , rs.getInt("NEWCAR_ID"), rs.getInt("OLDCAR_ID"), rs.getString("CAR_NAME"),
                      rs.getInt("CAR_CONDITION") ,rs.getInt("CAR_MODEL")));
            }

            custId.setCellValueFactory(new PropertyValueFactory<>("custId"));
            manId.setCellValueFactory(new PropertyValueFactory<>("manId"));
            newCarId.setCellValueFactory(new PropertyValueFactory<>("newCarId"));
            oldCarId.setCellValueFactory(new PropertyValueFactory<>("oldCarId"));
            carName.setCellValueFactory(new PropertyValueFactory<>("carName"));
            carCondition.setCellValueFactory(new PropertyValueFactory<>("carCondition"));
            carModel.setCellValueFactory(new PropertyValueFactory<>("carModel"));


            localRecord.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void checkAllStock_1(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("checkStockOwn1.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
