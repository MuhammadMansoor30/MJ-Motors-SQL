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

public class CheckStockLocalController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TableView<Cars> newCarLocal, oldCarLocal;
    @FXML
    TableColumn<Cars, Integer> carIdNew, carIdOld;
    @FXML
    TableColumn<Cars, String> carNameNew, carNameOld;
    @FXML
    TableColumn<Cars, Integer> carPriceNew, carPriceOld;
    @FXML
    TableColumn<Cars, String> carTypeNew, carTypeOld;
    @FXML
    TableColumn<Cars, Integer> carConditionNew, carConditionOld;
    @FXML
    TableColumn<Cars, Integer> carModelNew, carModelOld;

    ObservableList<Cars> newList = FXCollections.observableArrayList();
    ObservableList<Cars> oldList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM NEWLOCALCARS");
            while (rs.next()) {
                newList.add(new Cars(rs.getInt("CAR_ID"), rs.getInt("CAR_CONDITION"),
                        rs.getString("CAR_NAME"), rs.getInt("CAR_MODEL"), rs.getInt("CAR_PRICE"), rs.getString("CAR_TYPE")));
            }

            carIdNew.setCellValueFactory(new PropertyValueFactory<>("carId"));
            carNameNew.setCellValueFactory(new PropertyValueFactory<>("carName"));
            carPriceNew.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
            carModelNew.setCellValueFactory(new PropertyValueFactory<>("carModel"));
            carConditionNew.setCellValueFactory(new PropertyValueFactory<>("carCondition"));
            carTypeNew.setCellValueFactory(new PropertyValueFactory<>("carType"));

            newCarLocal.setItems(newList);

            ResultSet rs_1 = connection.createStatement().executeQuery("SELECT * FROM OLDLOCALCARS");
            while (rs_1.next()) {
                oldList.add(new Cars(rs_1.getInt("CAR_ID"), rs_1.getInt("CAR_CONDITION"),
                        rs_1.getString("CAR_NAME"), rs_1.getInt("CAR_MODEL"), rs_1.getInt("CAR_PRICE"), rs_1.getString("CAR_TYPE")));
            }

            carIdOld.setCellValueFactory(new PropertyValueFactory<>("carId"));
            carNameOld.setCellValueFactory(new PropertyValueFactory<>("carName"));
            carPriceOld.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
            carModelOld.setCellValueFactory(new PropertyValueFactory<>("carModel"));
            carConditionOld.setCellValueFactory(new PropertyValueFactory<>("carCondition"));
            carTypeOld.setCellValueFactory(new PropertyValueFactory<>("carType"));

            oldCarLocal.setItems(oldList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("owner1.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void moreOptions(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("localOwnOptions.fxml"));
        root = loader.load();
       // root = FXMLLoader.load(getClass().getResource("localOwnOptions.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
