package com.example.mj_motors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.*;

import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class Db_Connection extends Application {
    public static String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static String username = "MJ_MOTORS";
    public static String password = "hello";

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setTitle("MJ_MOTORS");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        stage.show();

    }

    public static void main(String[] args) {
        Db_Connection conn = new Db_Connection();

        try {
            Connection connection = DriverManager.getConnection(conn.dbURL, conn.username, conn.password);
            System.out.println("Connected to oracle database");

//            String sql2 = "INSERT INTO MANAGERS(MAN_ID , OWNER_ID , MAN_NAME , MAN_PHNUMBER , MAN_AGE) " + "VALUES(11 ,124, 'Asif Baloch' , 03330678951 , 34)";
//            String sql3 = "INSERT INTO MANAGERS(MAN_ID , OWNER_ID , MAN_NAME , MAN_PHNUMBER , MAN_AGE) " + "VALUES(13 ,124, 'Saleem Ahmed' , 03330448151 , 27)";
//            String sql4 = "INSERT INTO MANAGERS(MAN_ID , OWNER_ID , MAN_NAME , MAN_PHNUMBER , MAN_AGE) " + "VALUES(21 ,125, 'Waseem Bhuta' , 03331208938 , 38)";
//            String sql5 = "INSERT INTO MANAGERS(MAN_ID , OWNER_ID , MAN_NAME , MAN_PHNUMBER , MAN_AGE) " + "VALUES(23,125, 'Qaiser Majeed' , 03339669954 , 42)";
////            String sql = "INSERT INTO OWNERS (OWNER_ID , OWNER_NAME , OWNER_PASS) " + "VALUES(125 , 'MUHAMMAD MANSOOR' , 'owner2')";
//            Statement statement2 = connection.createStatement();
//            int rows = statement2.executeUpdate(sql5);
//
//            if (rows>0){
//                System.out.println("manger has been added successfully ");
//                statement2.close();
//
//            }


//            String sql = "INSERT INTO OWNERS (OWNER_ID , OWNER_NAME , OWNER_PASS) " + "VALUES(124 , 'SYED ALI JUNAID' , 'owner1')";
////            String sql = "INSERT INTO OWNERS (OWNER_ID , OWNER_NAME , OWNER_PASS) " + "VALUES(125 , 'MUHAMMAD MANSOOR' , 'owner2')";
//            Statement statement = connection.createStatement();
//            int rows = statement.executeUpdate(sql);
//
//            if (rows>0){
//                System.out.println("Row has been added successfully ");
//                statement.close();
//
//            }
//            connection.close();
            String sql = "SELECT * FROM OWNERS";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int owner_id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String pass = resultSet.getString(3);
                System.out.println(owner_id);
                System.out.println(name);
                System.out.println(pass);

            }


            String sql1 = "SELECT * FROM CUSTOMER";

            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);
            while (resultSet1.next()) {
                int customer_id = resultSet1.getInt(1);
                String customer_name = resultSet1.getString(2);
                int customer_phno = resultSet1.getInt(3);
                int customer_address = resultSet1.getInt(4);
                String customer_pass = resultSet1.getString(5);
                int customer_cnic = resultSet1.getInt(6);

                System.out.println(customer_id + " " + customer_name + " " + customer_phno + " " + customer_address + " " + customer_pass + " " + customer_cnic);

            }
        } catch (SQLException e) {
            System.out.println("Oops! error");
            e.printStackTrace();
        }


        launch();
    }

    public void getRegistered(int userId, String userName, String userPass, int userAddress, int userPhNo, int userCNIC) {


        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            String sqlTbl = "INSERT INTO CUSTOMER (CUST_ID, CUST_NAME, CUST_PHNUMBER, CUST_ADDRESS, CUST_PASS, CUST_CNIC)";
            String sqlVal = "VALUES ('" + userId + "', '" + userName + "', '" + userPhNo + "', '" + userAddress
                    + "', '" + userPass + "', '" + userCNIC + "')";
            String sql = sqlTbl + sqlVal;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                System.out.println("Inserted successfully");
                statement.close();
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }


    }

    public boolean checkCustomerLoginCredentials(String cusName, String custPass, int custId) {
        boolean valid = false;
        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            String sql = "SELECT *  FROM CUSTOMER";

            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(sql);
            while (resultSet1.next()) {
                int customer_id = resultSet1.getInt(1);
                String customer_name = resultSet1.getString(2);
                String customer_pass = resultSet1.getString(5);


                if (customer_id != custId && !Objects.equals(customer_name, cusName) && !Objects.equals(customer_pass, custPass)) {
                    System.out.println("user does not Exists");

                } else {
                    System.out.println("user Exists");
                    valid = true;
                }


            }
        } catch
        (SQLException e) {
            e.printStackTrace();

        }
        return valid;


    }


    public boolean checkOwnerLoginCredentials(int ownerId, String ownerName, String ownerPass) {
        boolean valid = false;
        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            String sql = "SELECT *  FROM OWNERS";

            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(sql);
            while (resultSet1.next()) {
                int own_id = resultSet1.getInt(1);
                String own_name = resultSet1.getString(2);
                String own_pass = resultSet1.getString(3);


                if (ownerId != own_id && !Objects.equals(ownerName, own_name) && !Objects.equals(ownerPass, own_pass)) {
                    System.out.println("Owner Exists");

                } else {
                    valid = true;
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return valid;


    }


    public boolean checkManagerLoginCredentials(int ownID, int manId) {
        boolean valid = false;
        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            String sql = "SELECT *  FROM MANAGERS";

            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(sql);
            while (resultSet1.next()) {
                int man_id = resultSet1.getInt(1);
                int own_id = resultSet1.getInt(5);


                if (man_id == manId && own_id == ownID) {
                    System.out.println("MANAGER Exists");
                    valid = true;
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return valid;


    }

    public void addManagerLocalCarsToDb(int no ,int carId, String carName, int carPrice, int carModel, int carCondition, String carType) {

        if(no==1) {
            try {
                Connection connection = DriverManager.getConnection(dbURL, username, password);
                String sqlTbl = "INSERT INTO NEWLOCALCARS (CAR_ID, CAR_NAME,CAR_TYPE , CAR_PRICE , CAR_CONDITION ,CAR_MODEL)";
                String sqlVal = "VALUES ('" + carId + "', '" + carName + "', '" + carType + "', '" + carPrice
                        + "', '" + carCondition + "', '" + carModel + "')";
                String sql = sqlTbl + sqlVal;
                Statement statement = connection.createStatement();
                int rows = statement.executeUpdate(sql);
                if (rows > 0) {
                    System.out.println(" Local New Cars Inserted successfully");
                    statement.close();
                }
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();

            }


        } else if (no == 2){
            try {
                Connection connection = DriverManager.getConnection(dbURL, username, password);
                String sqlTbl = "INSERT INTO OLDLOCALCARS (CAR_ID, CAR_NAME,CAR_TYPE , CAR_PRICE , CAR_CONDITION ,CAR_MODEL)";
                String sqlVal = "VALUES ('" + carId + "', '" + carName + "', '" + carType + "', '" + carPrice
                        + "', '" + carCondition + "', '" + carModel + "')";
                String sql = sqlTbl + sqlVal;
                Statement statement = connection.createStatement();
                int rows = statement.executeUpdate(sql);
                if (rows > 0) {
                    System.out.println(" Local Old Cars Inserted successfully");
                    statement.close();
                }

                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();

            }


        }



    }

    public void addManagerImportedCarsToDb(int no ,int carId, String carName, int carPrice, int carModel, int carCondition, String carType,
                                           int carYearOfImport, String carAuctionSheet, int carGrade) {

        if(no==1) {
            try {
                Connection connection = DriverManager.getConnection(dbURL, username, password);
                String sqlTbl = "INSERT INTO NEWIMPORTEDCARS (CAR_ID, CAR_NAME,CAR_TYPE , CAR_PRICE , CAR_CONDITION ,CAR_MODEL, " +
                        "CAR_YEAROFIMPORT, CAR_AUCTIONSHEET, CAR_GRADE)";
                String sqlVal = "VALUES ('" + carId + "', '" + carName + "', '" + carType + "', '" + carPrice
                        + "', '" + carCondition + "', '" + carModel + "', '" + carYearOfImport + "', '" + carAuctionSheet
                        + "', '" + carGrade + "')";
                String sql = sqlTbl + sqlVal;
                Statement statement = connection.createStatement();
                int rows = statement.executeUpdate(sql);
                if (rows > 0) {
                    System.out.println(" Imported New Cars Inserted successfully");
                    statement.close();
                }
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();

            }


        } else if (no == 2){
            try {
                Connection connection = DriverManager.getConnection(dbURL, username, password);
                String sqlTbl = "INSERT INTO OLDIMPORTEDCARS (CAR_ID, CAR_NAME,CAR_TYPE , CAR_PRICE , CAR_CONDITION ,CAR_MODEL, " +
                        "CAR_YEAROFIMPORT, CAR_AUCTIONSHEET, CAR_GRADE)";
                String sqlVal = "VALUES ('" + carId + "', '" + carName + "', '" + carType + "', '" + carPrice
                        + "', '" + carCondition + "', '" + carModel + "', '" + carYearOfImport + "', '" + carAuctionSheet
                        + "', '" + carGrade + "')";
                String sql = sqlTbl + sqlVal;
                Statement statement = connection.createStatement();
                int rows = statement.executeUpdate(sql);
                if (rows > 0) {
                    System.out.println(" Imported Old Cars Inserted successfully");
                    statement.close();
                }
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();

            }


        }



    }

    public void importedCarSale(int carId, String carName, int carPrice, int carModel, int carCondition, String carType,
                                     int carYearOfImport, String carAuctionSheet, int carGrade) {

        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            String sqlTbl = "INSERT INTO IMPORTEDCUSTOMERSALE (CAR_ID, CAR_NAME,CAR_TYPE , CAR_PRICE , CAR_CONDITION ,CAR_MODEL, " +
                    "CAR_YEAROFIMPORT, CAR_AUCTIONSHEET, CAR_GRADE)";
            String sqlVal = "VALUES ('" + carId + "', '" + carName + "', '" + carType + "', '" + carPrice
                    + "', '" + carCondition + "', '" + carModel + "', '" + carYearOfImport + "', '" + carAuctionSheet
                    + "', '" + carGrade + "')";
            String sql = sqlTbl + sqlVal;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                System.out.println(" Car Purchased successfully");
                statement.close();
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }



    }

    public void placeImportedCarSale(int carId, String carName, int carModel, int carCondition, String carType,
                                           int carYearOfImport, String carAuctionSheet, int carGrade, int carCommission) {

        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            String sqlTbl = "INSERT INTO IMPORTEDCOMMISSION (CAR_ID, CAR_NAME,CAR_TYPE , CAR_CONDITION ,CAR_MODEL, " +
                    "CAR_YEAROFIMPORT, CAR_AUCTIONSHEET, CAR_GRADE, IMPORTED_COMM)";
            String sqlVal = "VALUES ('" + carId + "', '" + carName + "', '" + carType + "', '"
                    + carCondition + "', '" + carModel + "', '" + carYearOfImport + "', '" + carAuctionSheet
                    + "', '" + carGrade + "', '" + carCommission + "')";
            String sql = sqlTbl + sqlVal;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                System.out.println(" Car Placed successfully");
                statement.close();
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }



    }
    public void localCarSale(int carId, String carName, int carPrice, int carModel, int carCondition, String carType) {

        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            String sqlTbl = "INSERT INTO LOCALCUSTOMERSALE (CAR_ID, CAR_NAME,CAR_TYPE , CAR_PRICE , CAR_CONDITION ,CAR_MODEL)";
            String sqlVal = "VALUES ('" + carId + "', '" + carName + "', '" + carType + "', '" + carPrice
                    + "', '" + carCondition + "', '" + carModel + "')";
            String sql = sqlTbl + sqlVal;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                System.out.println(" Car Purchased successfully");
                statement.close();
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }



    }
    public void placeLocalCarSale(int carId, String carName,int carModel, int carCondition, String carType, int carCommission) {

        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            String sqlTbl = "INSERT INTO LOCALCOMMISSION (CAR_ID, CAR_NAME,CAR_TYPE ,CAR_CONDITION ,CAR_MODEL, LOCAL_COMM)";
            String sqlVal = "VALUES ('" + carId + "', '" + carName + "', '" + carType + "', '"
                    + carCondition + "', '" + carModel + "', '" + carCommission + "')";
            String sql = sqlTbl + sqlVal;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                System.out.println(" Car Purchased successfully");
                statement.close();
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }



    }
    public static Connection provideConnection() throws  SQLException{

        Connection connection = DriverManager.getConnection(dbURL, username, password);

        return connection;

    }
    public void addCartoLocalCustomerDb(int no ,int carId, String carName, int carPrice, int carModel, int carCondition, String carType) {
        try {
            Connection connection = DriverManager.getConnection(dbURL, username, password);
            String sqlTbl = "INSERT INTO LOCALCUSTOMERSALE (CAR_ID, CAR_NAME,CAR_TYPE , CAR_PRICE , CAR_CONDITION ,CAR_MODEL)";
            String sqlVal = "VALUES ('" + carId + "', '" + carName + "', '" + carType + "', '" + carPrice
                    + "', '" + carCondition + "', '" + carModel + "')";
            String sql = sqlTbl + sqlVal;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                System.out.println(" cars Inserted successfully");
                statement.close();
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }




    }



}