package com.example.mj_motors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.lang.Integer.lowestOneBit;
import static java.lang.Integer.parseInt;

public class NewLocalSectionController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public int value;

    @FXML
    AnchorPane paneNew1, paneNew2, pane1, pane2;
    @FXML
    TableView<Cars> newCarlocal;
    @FXML
    TableColumn<Cars, Integer> carId;
    @FXML
    TableColumn<Cars, String> carName;
    @FXML
    TableColumn<Cars, Integer> carPrice;
    @FXML
    TableColumn<Cars, String> carType;
    @FXML
    TableColumn<Cars, Integer> carModel;
    @FXML
    TableColumn<Cars, Integer> carCondition;
    @FXML
    TextField searchNew, carIdUserNew, userPrice;
    @FXML
    Label price, priceDeneWala, idDeneWala;
    public boolean check = false;



    ObservableList<Cars> list = FXCollections.observableArrayList();
    ObservableList<Cars> searchList = FXCollections.observableArrayList();
    ObservableList<Cars> selectedItem = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM NEWLOCALCARS");
            while (rs.next()) {
                list.add(new Cars(rs.getInt("CAR_ID"), rs.getInt("CAR_CONDITION"),
                        rs.getString("CAR_NAME"), rs.getInt("CAR_MODEL"), rs.getInt("CAR_PRICE"), rs.getString("CAR_TYPE")));
            }

            carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
            carName.setCellValueFactory(new PropertyValueFactory<>("carName"));
            carPrice.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
            carModel.setCellValueFactory(new PropertyValueFactory<>("carModel"));
            carCondition.setCellValueFactory(new PropertyValueFactory<>("carCondition"));
            carType.setCellValueFactory(new PropertyValueFactory<>("carType"));

            newCarlocal.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void buyNewCar(ActionEvent actionEvent) throws IOException, SQLException {


        System.out.println(selectedItem.get(0).carPrice);
        Connection connection = Db_Connection.provideConnection();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM NEWLOCALCARS");
        int car_id = 0;
        while (rs.next()) {
            car_id = rs.getInt(1);
            if (carIdUserNew.getText().isEmpty()) {
                invalidCarDialog();
            } else if (parseInt(carIdUserNew.getText()) == car_id) {
                value = parseInt(carIdUserNew.getText());
                idDeneWala.setText(carIdUserNew.getText());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("buyNewCar.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

            }
        }

    }

    @FXML
    protected void searchOtherNewCar() {
        paneNew2.setVisible(false);
        paneNew1.setVisible(true);
        searchNew.clear();
        newCarlocal.setItems(list);
        selectedItem  = FXCollections.observableArrayList();

    }

    @FXML
    protected void proceedNew(ActionEvent actionEvent) throws IOException ,SQLException {

        paneNew1.setVisible(false);
        paneNew2.setVisible(true);
        System.out.println(carIdUserNew.getText());
        selectedItem.add(newCarlocal.getSelectionModel().getSelectedItem());
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
        System.out.println(selectedItem.get(0).carId);


    }

    @FXML
    protected void search(ActionEvent actionEvent) throws IOException, SQLException {

        Connection connection = Db_Connection.provideConnection();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM NEWLOCALCARS WHERE CAR_NAME = '" + searchNew.getText() + "'");
        if (searchNew.getText().isEmpty()) {
            carsEmptyCredentialsDialog();
        }

        while (rs.next()) {

            searchList = FXCollections.observableArrayList();
            searchList.add(new Cars(rs.getInt("CAR_ID"), rs.getInt("CAR_PRICE"),
                    rs.getString("CAR_NAME"), rs.getInt("CAR_MODEL"), rs.getInt("CAR_CONDITION"), rs.getString("CAR_TYPE")));


        }
        newCarlocal.setItems(searchList);
    }

    @FXML
    protected void carsNotFoundDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("Car Not Found! Please check your input text");
            alert.showAndWait();
            newCarlocal.setItems(list);
        });

    }

    @FXML
    protected void carsEmptyCredentialsDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("Please fill the required Credentials");
            alert.showAndWait();
            newCarlocal.setItems(list);
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
