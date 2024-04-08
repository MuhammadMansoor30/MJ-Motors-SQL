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
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class AddOldImportController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    AnchorPane pane1, pane2;

    @FXML
    private Spinner<Integer> spnModel, spnCondition, spnCarGrade, spnYearOfImport;

    @FXML
    private ChoiceBox<String> chBoxType, chBoxAuctionSht;

    @FXML
    private TextField carNameAdd, carPriceAdd, userCarId;
    @FXML
    TableView<Cars> oldCarImported;
    @FXML
    TableColumn<Cars, Integer> carId;
    @FXML
    TableColumn<Cars, String> carName;
    @FXML
    TableColumn<Cars, Integer> carModel;
    @FXML
    TableColumn<Cars, Integer> carPrice;
    @FXML
    TableColumn<Cars, String> carType;
    @FXML
    TableColumn<Cars, Integer> carCondition;
    @FXML
    TableColumn<Cars, Integer> carImportYear;
    @FXML
    TableColumn<Cars, String> carAuction;
    @FXML
    TableColumn<Cars, Integer> carGrade;


    private String[] carTypes = {"HatchBack", "Sedan", "Crossover", "SUV", "Sports"};

    private String[] options = {"Yes! Available", "Not Available"};

    ObservableList<Cars> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        spnCondition.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2008, 2020, 1);
        spnModel.setValueFactory(valueFactory1);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2012, 2022, 1);
        spnYearOfImport.setValueFactory(valueFactory2);

        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
        spnCarGrade.setValueFactory(valueFactory3);

        chBoxType.getItems().addAll(carTypes);

        chBoxAuctionSht.getItems().addAll(options);
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM IMPORTEDCUSTOMERSALE");
            while (rs.next()) {

                list.add(new Cars(rs.getInt("CAR_ID"), rs.getInt("CAR_CONDITION"),
                        rs.getString("CAR_NAME"), rs.getInt("CAR_MODEL"), rs.getInt("CAR_PRICE"), rs.getString("CAR_TYPE")
                        , rs.getInt("CAR_YEAROFIMPORT") , rs.getString("CAR_AUCTIONSHEET"), rs.getInt("CAR_GRADE")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        carName.setCellValueFactory(new PropertyValueFactory<>("carName"));
        carPrice.setCellValueFactory(new PropertyValueFactory<>("carPrice"));
        carModel.setCellValueFactory(new PropertyValueFactory<>("carModel"));
        carCondition.setCellValueFactory(new PropertyValueFactory<>("carCondition"));
        carType.setCellValueFactory(new PropertyValueFactory<>("carType"));
        carImportYear.setCellValueFactory(new PropertyValueFactory<>("carImportYear"));
        carAuction.setCellValueFactory(new PropertyValueFactory<>("carAuction"));
        carGrade.setCellValueFactory(new PropertyValueFactory<>("carGrade"));

        oldCarImported.setItems(list);
    }

    @FXML
    protected void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("manLoginImported.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("man.css");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    protected void addCarFromTable(){
        pane1.setVisible(false);
        pane2.setVisible(true);
    }

    @FXML
    protected void goBack(){
        pane1.setVisible(true);
        pane2.setVisible(false);
    }

    @FXML
    protected void addOldImportCar(ActionEvent actionEvent) throws IOException, SQLException {

        String carName = carNameAdd.getText();
        String carPrice = carPriceAdd.getText();
        String carModel = spnModel.getValue().toString();
        String carCondition = spnCondition.getValue().toString();
        String carType = chBoxType.getValue();
        String carAuctionSheet = chBoxAuctionSht.getValue();
        String carGrade = spnCarGrade.getValue().toString();
        String carYearOfImport = spnYearOfImport.getValue().toString();
        int carId = (int) (Math.random() * 2000) + 1; // generate between 1001 and 2000

        Db_Connection regConn = new Db_Connection();
        if (carName.isEmpty() || carPrice.isEmpty() || chBoxType.getSelectionModel().isEmpty() || chBoxAuctionSht.getSelectionModel().isEmpty()) {
            carsEmptyCredentialsDialog();
        } else {

            if (checkInt(carPriceAdd)) {
                regConn.addManagerImportedCarsToDb(2 , carId, carName, parseInt(carPrice), parseInt(carModel), parseInt(carCondition), carType,
                        parseInt(carYearOfImport), carAuctionSheet, parseInt(carGrade));
                Connection connection = Db_Connection.provideConnection();
                String sqlTbl1 = "INSERT INTO ADDIMPORTED (MAN_ID , OLDCAR_ID)";
                String sqlVal1 = "VALUES ('" + 23 + "', '" + carId + "')";
                String sql2 = sqlTbl1 + sqlVal1;
                Statement statement2= connection.createStatement();
                int rows1 = statement2.executeUpdate(sql2);
                if (rows1 > 0) {
                    statement2.close();

                    // connection.close();

                }
                carAddSuccessDialog();
                carNameAdd.clear();
                carPriceAdd.clear();


            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Caution!");
                    alert.setContentText("Oops ! Looks like some entered Values are not a number. Please Try Again ");
                    alert.showAndWait();
                });
                carNameAdd.clear();
                carPriceAdd.clear();

            }
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
    private boolean checkInt(TextField carPrice) {
        try {
            Integer.parseInt(carPrice.getText());
            return true;
        } catch (NumberFormatException e) {

            return false;
        }
    }

    @FXML
    protected void carAddSuccessDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setContentText("CAR HAS BEEN ADDED SUCCESSFULLY ");
            alert.showAndWait();
        });

    }
    @FXML
    protected void addCartoOldStock() throws SQLException {
        int value = parseInt(userCarId.getText());
        Connection connection = Db_Connection.provideConnection();
        String sql = "SELECT * FROM IMPORTEDCUSTOMERSALE WHERE CAR_ID = '" + value + "'";

        Statement statement = connection.createStatement();
        ResultSet resultSet1 = statement.executeQuery(sql);
        while (resultSet1.next()) {

            int car_id = resultSet1.getInt(1);
            String car_name = resultSet1.getString(2);
            String car_type = resultSet1.getString(3);
            int car_price = resultSet1.getInt(4);
            int car_condition = resultSet1.getInt(5);
            int car_model = resultSet1.getInt(6);
            int yearOfImport = resultSet1.getInt(7);
            String auctionSheet = resultSet1.getString(8);
            int car_grade = resultSet1.getInt(9);


            String sqlTbl = "INSERT INTO OLDIMPORTEDCARS (CAR_ID, CAR_NAME,CAR_TYPE , CAR_PRICE , CAR_CONDITION ,CAR_MODEL, " +
                    "CAR_YEAROFIMPORT, CAR_AUCTIONSHEET, CAR_GRADE)";
            String sqlVal = "VALUES ('" + car_id + "', '" + car_name + "', '" + car_type + "', '" + car_price
                    + "', '" + car_condition + "', '" + car_model + "', '" + yearOfImport + "', '" + auctionSheet
                    + "', '" + car_grade + "')";
            String sql1 = sqlTbl + sqlVal;
            Statement statement1 = connection.createStatement();
            int rows = statement1.executeUpdate(sql1);
            if (rows > 0) {
                statement1.close();

                // connection.close();

            }
            String sqlTbl1 = "INSERT INTO ADDIMPORTED (MAN_ID , OLDCAR_ID)";
            String sqlVal1 = "VALUES ('" + 23 + "', '" + value + "')";
            String sql2 = sqlTbl1 + sqlVal1;
            Statement statement2= connection.createStatement();
            int rows1 = statement2.executeUpdate(sql2);
            if (rows1 > 0) {
                statement2.close();

                // connection.close();

            }
            String sqlDel = "DELETE  FROM IMPORTEDCUSTOMERSALE WHERE CAR_ID =('" + value + "')";
            Statement st = connection.createStatement();
            int rows3 = st.executeUpdate(sqlDel);
            if (rows3 > 0) {
                st.close();

                connection.close();
            }


        }


    }
}
