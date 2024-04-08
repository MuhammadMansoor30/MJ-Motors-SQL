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

public class CheckStockImportedController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TableView<Cars> newCarImported, oldCarImported;
    @FXML
    TableColumn<Cars, Integer> carIdNew, carIdOld;
    @FXML
    TableColumn<Cars, String> carNameNew, carNameOld;
    @FXML
    TableColumn<Cars, String> carTypeNew, carTypeOld;
    @FXML
    TableColumn<Cars, Integer> carConditionNew, carConditionOld;
    @FXML
    TableColumn<Cars, Integer> carModelNew, carModelOld;
    @FXML
    TableColumn<Cars, Integer> carPriceNew, carPriceOld;
    @FXML
    TableColumn<Cars, Integer> carYearImportNew, carYearImportOld;
    @FXML
    TableColumn<Cars, Integer> carAuctionNew, carAuctionOld;
    @FXML
    TableColumn<Cars, Integer> carGradeNew, carGradeOld;

    ObservableList<Cars> newList = FXCollections.observableArrayList();
    ObservableList<Cars> oldList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM NEWIMPORTEDCARS");
            while (rs.next()) {
                newList.add(new Cars(rs.getInt("CAR_ID"), rs.getInt("CAR_CONDITION"),
                        rs.getString("CAR_NAME"), rs.getInt("CAR_MODEL"), rs.getInt("CAR_PRICE"), rs.getString("CAR_TYPE")
                        , rs.getInt("CAR_YEAROFIMPORT") , rs.getString("CAR_AUCTIONSHEET"), rs.getInt("CAR_GRADE")));
            }

            carIdNew.setCellValueFactory(new PropertyValueFactory<>("carId"));
            carNameNew.setCellValueFactory(new PropertyValueFactory<>("carName"));
            carPriceNew.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
            carModelNew.setCellValueFactory(new PropertyValueFactory<>("carModel"));
            carConditionNew.setCellValueFactory(new PropertyValueFactory<>("carCondition"));
            carTypeNew.setCellValueFactory(new PropertyValueFactory<>("carType"));
            carYearImportNew.setCellValueFactory(new PropertyValueFactory<>("carImportYear"));
            carAuctionNew.setCellValueFactory(new PropertyValueFactory<>("carAuction"));
            carGradeNew.setCellValueFactory(new PropertyValueFactory<>("carGrade"));

            newCarImported.setItems(newList);

            ResultSet rs_1 = connection.createStatement().executeQuery("SELECT * FROM OLDIMPORTEDCARS");
            while (rs_1.next()) {
                newList.add(new Cars(rs_1.getInt("CAR_ID"), rs_1.getInt("CAR_CONDITION"),
                        rs_1.getString("CAR_NAME"), rs_1.getInt("CAR_MODEL"), rs_1.getInt("CAR_PRICE"), rs_1.getString("CAR_TYPE")
                        , rs_1.getInt("CAR_YEAROFIMPORT") , rs_1.getString("CAR_AUCTIONSHEET"), rs_1.getInt("CAR_GRADE")));
            }

            carIdOld.setCellValueFactory(new PropertyValueFactory<>("carId"));
            carNameOld.setCellValueFactory(new PropertyValueFactory<>("carName"));
            carPriceOld.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
            carModelOld.setCellValueFactory(new PropertyValueFactory<>("carModel"));
            carConditionOld.setCellValueFactory(new PropertyValueFactory<>("carCondition"));
            carTypeOld.setCellValueFactory(new PropertyValueFactory<>("carType"));
            carYearImportOld.setCellValueFactory(new PropertyValueFactory<>("carImportYear"));
            carAuctionOld.setCellValueFactory(new PropertyValueFactory<>("carAuction"));
            carGradeOld.setCellValueFactory(new PropertyValueFactory<>("carGrade"));

            oldCarImported.setItems(oldList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void back_1(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("owner2.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void moreOptions(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("importedOwnOptions.fxml"));
        root = loader.load();
        //root = FXMLLoader.load(getClass().getResource("importedOwnOptions.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
