package com.example.mj_motors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class ConfirmNewImportedController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    AnchorPane pane1, pane2;
    @FXML
    TextField userPrice;
    @FXML
    Label price;
    public int car_ID;
    public int cust_ID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int car_price = 0;
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs1 = connection.createStatement().executeQuery("SELECT * FROM PRICE");
            while (rs1.next()) {
                car_price = rs1.getInt(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        int car_id = 0;
        try {
            Connection connection = Db_Connection.provideConnection();
            ResultSet rs1 = connection.createStatement().executeQuery("SELECT * FROM ID");

            while (rs1.next()) {
                car_id = rs1.getInt(1);
                car_ID = car_id;


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        price.setText(String.valueOf(car_price));


    }

    @FXML
    protected void backToMenu(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("importedCarsSection.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void confirmPurchase() throws SQLException {
//        pane1.setVisible(false);
//        pane2.setVisible(true);
        int labelPrice = parseInt(price.getText());
        int priceOfUser = parseInt(userPrice.getText());
        if (priceOfUser < labelPrice) {
            sorryDialog();
            userPrice.clear();
        } else if (priceOfUser >= labelPrice) {
            carsToCust();
            successDialog();
            userPrice.clear();

        }
    }

    protected void sorryDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("We Cannot Sell This Car for this price ");
            alert.showAndWait();
        });

    }

    protected void successDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setContentText("Congratulation You have Purchased this Car");
            alert.showAndWait();
        });

    }

    @FXML
    public void carsToCust() throws SQLException {

        if (car_ID != 0) {
            Connection connection = Db_Connection.provideConnection();
            String sql = "SELECT * FROM NEWIMPORTEDCARS WHERE CAR_ID = '" + car_ID + "'";

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


                String sqlTbl = "INSERT INTO IMPORTEDNEWCARS (CAR_ID, CAR_NAME,CAR_TYPE , CAR_PRICE , CAR_CONDITION ,CAR_MODEL, " +
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


                String sqlTbl2 = "INSERT INTO IMPORTEDPURCHASES(CUST_ID, MAN_ID,NEWCAR_ID)";
                String sqlVal2 = "VALUES ('" + cust_ID + "', '" + 21 + "', '" + car_ID + "')";
                String sql2 = sqlTbl2 + sqlVal2;
                Statement statement2 = connection.createStatement();
                int rows2 = statement2.executeUpdate(sql2);
                if (rows2 > 0) {
                    statement2.close();

                    // connection.close();
                }
                String sqlDel = "DELETE  FROM NEWIMPORTEDCARS WHERE CAR_ID =('" + car_ID + "')";
                Statement st = connection.createStatement();
                int rows3 = st.executeUpdate(sqlDel);
                if (rows3 > 0) {
                    st.close();

//                    connection.close();
                }

            }


        } else {
            System.out.println(car_ID + "is null");
        }


    }
}
