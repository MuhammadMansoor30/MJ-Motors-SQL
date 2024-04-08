package com.example.mj_motors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class OldLocalSectionController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    AnchorPane pane1, pane2;

    @FXML
    AnchorPane paneOld1, paneOld2;

    @FXML
    public TableView<Cars> oldCarLocal;

    @FXML
    public TableColumn<Cars, Integer> carId;
    @FXML
    public TableColumn<Cars, String> carName;
    @FXML
    public TableColumn<Cars, Integer> carPrice;
    @FXML
    public TableColumn<Cars, String> carType;
    @FXML
    public TableColumn<Cars, Integer> carModel;
    @FXML
    public TableColumn<Cars, Integer> carCondition;
    @FXML
    public TextField searchOld, userPrice, carIdUserOld;
    @FXML
    public Label price;

    ObservableList<Cars> list = FXCollections.observableArrayList();
    ObservableList<Cars> searchList = FXCollections.observableArrayList();
    ObservableList<Cars> selectedItem = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM OLDLOCALCARS");
            while (rs.next()) {

                list.add(new Cars(rs.getInt("CAR_ID"), rs.getInt("CAR_CONDITION"),
                        rs.getString("CAR_NAME"), rs.getInt("CAR_MODEL"), rs.getInt("CAR_PRICE"), rs.getString("CAR_TYPE")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
            carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
            carName.setCellValueFactory(new PropertyValueFactory<>("carName"));
            carCondition.setCellValueFactory(new PropertyValueFactory<>("carCondition"));
            carModel.setCellValueFactory(new PropertyValueFactory<>("carModel"));
            carPrice.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
            carType.setCellValueFactory(new PropertyValueFactory<>("carType"));


            oldCarLocal.setItems(list);

    }

    @FXML
    protected void proceedOld() throws SQLException {
        paneOld1.setVisible(false);
        paneOld2.setVisible(true);
        System.out.println(carIdUserOld.getText());
        selectedItem.add(oldCarLocal.getSelectionModel().getSelectedItem());
        Connection connection = Db_Connection.provideConnection();
        String sqlTbl = "INSERT INTO PRICE (CAR_PRICE)";
        String sqlVal = "VALUES ('" + selectedItem.get(0).carPrice + "')";
        String sql = sqlTbl + sqlVal;
        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate(sql);
        if (rows > 0) {
            statement.close();
        }
        String sqlTbl1 = "INSERT INTO ID (CAR_ID)";
        String sqlVal1 = "VALUES ('" + selectedItem.get(0).carId + "')";
        String sql1 = sqlTbl1 + sqlVal1;
        Statement statement1 = connection.createStatement();
        int rows1 = statement1.executeUpdate(sql1);
        if (rows1 > 0) {
            statement.close();
        }

        for(int i = 0 ; i<selectedItem.size() ; i++){
            System.out.println(selectedItem.get(i).carId);
        }

    }


    @FXML
    protected void buyOldCar(ActionEvent actionEvent) throws IOException, SQLException {
        Connection connection = Db_Connection.provideConnection();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM OLDLOCALCARS");
        int car_id= 0;
        while (rs.next()) {
            car_id = rs.getInt(1);
            if(carIdUserOld.getText().isEmpty()){
                invalidCarDialog();
            }
            else if(parseInt(carIdUserOld.getText()) == car_id){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("buyOldCar.fxml"));
                root = loader.load();
                stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }
        }


    }

    @FXML
    protected void searchOtherOldCar(){
        paneOld2.setVisible(false);
        paneOld1.setVisible(true);
        searchOld.clear();
        oldCarLocal.setItems(list);

    }

    @FXML
    protected void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("localCarsSection.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void searchCar() throws SQLException{
        Connection connection = Db_Connection.provideConnection();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM OLDLOCALCARS WHERE CAR_NAME = '" + searchOld.getText() + "'");
        if (searchOld.getText().isEmpty()){
            carsEmptyCredentialsDialog();
        }

        while (rs.next()) {

                searchList = FXCollections.observableArrayList();
                searchList.add(new Cars(rs.getInt("CAR_ID"), rs.getInt("CAR_PRICE"),
                        rs.getString("CAR_NAME"), rs.getInt("CAR_MODEL"), rs.getInt("CAR_CONDITION"), rs.getString("CAR_TYPE")));


        }
        oldCarLocal.setItems(searchList);

    }
    @FXML
    protected void carsNotFoundDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("Car Not Found! Please check your input text");
            alert.showAndWait();
            oldCarLocal.setItems(list);
        });

    }

    @FXML
    protected void carsEmptyCredentialsDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("Please fill the required Credentials");
            alert.showAndWait();
            oldCarLocal.setItems(list);
        });

    }

    @FXML
    protected void invalidCarDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setContentText("Invalid Car Id. Try Again ");
            alert.showAndWait();
        });

    }

}
