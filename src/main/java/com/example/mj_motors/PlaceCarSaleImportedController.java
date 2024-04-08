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

public class PlaceCarSaleImportedController implements Initializable {
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
    private TextField carName;
    @FXML
    private Label commission;

    private String[] carTypes = {"HatchBack", "Sedan", "Crossover", "SUV", "Sports"};

    private String[] values = {"Yes! Available", "Not Available"};
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

        String carType = chBoxType.getValue();
        if (carName.getText().isEmpty() || chBoxType.getValue().isEmpty()|| chBoxAuction.getValue().isEmpty() ||
                (!carName.getText().isEmpty() && chBoxType.getValue().isEmpty() && !chBoxAuction.getValue().isEmpty()) || (carName.getText().isEmpty() &&
                !chBoxType.getValue().isEmpty() && !chBoxAuction.getValue().isEmpty() ) || (!carName.getText().isEmpty() && !chBoxType.getValue().isEmpty() && chBoxAuction.getValue().isEmpty() ) ||
                (carName.getText().isEmpty() && chBoxType.getValue().isEmpty() && chBoxAuction.getValue().isEmpty() )) {

            pane2.setVisible(false);
            carsEmptyCredentialsDialog();
        }
        else {
            if(carType.equalsIgnoreCase("HATCHBACK")){
                commission.setText("20000");
                pane1.setVisible(false);
                pane2.setVisible(true);

            }
            else if(carType.equalsIgnoreCase("SEDAN")){
                commission.setText("25000");
                pane1.setVisible(false);
                pane2.setVisible(true);
            }
            else if(carType.equalsIgnoreCase("CROSSOVER")){
                commission.setText("30000");
                pane1.setVisible(false);
                pane2.setVisible(true);
            }
            else if(carType.equalsIgnoreCase("SUV")){
                commission.setText("40000");
                pane1.setVisible(false);
                pane2.setVisible(true);
            }
            else if(carType.equalsIgnoreCase("SPORTS")){
                commission.setText("50000");
                pane1.setVisible(false);
                pane2.setVisible(true);
            }

        }


    }

    @FXML
    protected void cancel(ActionEvent actionEvent) throws IOException {
        pane1.setVisible(true);
        pane2.setVisible(false);

    }
    @FXML
    protected void placeImportedCar(ActionEvent actionEvent) throws IOException, SQLException {
        String name = carName.getText();
        int carModel = parseInt(spnModel.getValue().toString());
        int carCondition = parseInt(spnCondition.getValue().toString());
        String carAuctionSheet = chBoxAuction.getValue();
        int carGrade = parseInt(spnGrade.getValue().toString());
        String carType = chBoxType.getValue();
        int carYearOfImport = parseInt(spnYearImport.getValue().toString());
        int carCommission = parseInt(commission.getText());
        int carId = (int) (Math.random() * 2000) + 1;

        Db_Connection regConn = new Db_Connection();
        if (name.isEmpty() && chBoxType.getSelectionModel().isEmpty() && chBoxAuction.getSelectionModel().isEmpty()) {
            carsEmptyCredentialsDialog();
        } else {
            regConn.placeImportedCarSale(carId, name, carModel, carCondition, carType,
                    carYearOfImport, carAuctionSheet, carGrade, carCommission);
            Connection connection = Db_Connection.provideConnection();
            String sqlTbl1 = "INSERT INTO IMPORTEDSALE (CUST_ID, MAN_ID , COMMCAR_ID)";
            String sqlVal1 = "VALUES ('" + cust_ID + "', '" + 23 + "', '" + carId + "')";
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
    protected void carAddSuccessDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setContentText("CAR HAS BEEN PLACED SUCCESSFULLY ");
            alert.showAndWait();
        });

    }
}
