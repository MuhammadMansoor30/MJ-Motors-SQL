package com.example.mj_motors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.SortedMap;

import static java.lang.Integer.parseInt;

public class SignInController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private RadioButton radioBtn1, radioBtn2;
    @FXML
    private AnchorPane owner, manager;

    @FXML
    private Button loginMan, loginOwn;

    @FXML
    private TextField userName, userPassword, userAddress, userPhNo, userCNIC;
    @FXML
    private TextField userLoginName, userLoginPassword, customerLoginId;
    @FXML
    private TextField ownName, idOwn;
    @FXML
    private PasswordField password;
    @FXML
    private TextField ownId, name, phNo, age, manId;


    @FXML
    protected void login(ActionEvent actionEvent) throws IOException, SQLException {
        String custName = userLoginName.getText();
        String custPass = userLoginPassword.getText();
        Db_Connection loginconn = new Db_Connection();

        if (custName.isEmpty() || customerLoginId.getText().isEmpty() || custPass.isEmpty()) {
            loginEmptyCredentialsDialog();

        } else {
            if (checkInt(customerLoginId)) {
                boolean valid = loginconn.checkCustomerLoginCredentials(custName, custPass, parseInt(customerLoginId.getText()));

                if (valid) {
                    loginSuccessDialog();
                    root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    scene.getStylesheets().add("styleMenu.css");
                    stage.setScene(scene);
                    stage.show();

                } else {

                    loginDenialDialog();
                    customerLoginId.clear();
                    userLoginName.clear();
                    userLoginPassword.clear();
                }

            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Caution!");
                    alert.setContentText("Oops ! Looks like entered Id Is not a number. Please Try Again ");
                    alert.showAndWait();
                });
                customerLoginId.clear();
                userLoginName.clear();
                userLoginPassword.clear();
            }
        }
    }


    @FXML
    protected void register(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("register.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void pressRegister(ActionEvent actionEvent) {
        String usName = userName.getText();
        String usPass = userPassword.getText();

        int usId = (int) (Math.random() * 10000) + 1;
        Db_Connection regConn = new Db_Connection();
        if (usName.isEmpty() || usPass.isEmpty() || userAddress.getText().isEmpty() || userCNIC.getText().isEmpty() || userPhNo.getText().isEmpty()) {
            registerEmptyCredentialsDialog();
        } else {
            if (checkInt(userAddress) && checkInt(userCNIC) && checkInt(userPhNo)) {
                regConn.getRegistered(usId, usName, usPass, parseInt(userAddress.getText()), parseInt(userPhNo.getText()), parseInt(userCNIC.getText()));

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setContentText("Registered Successfully");
                    Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            alert.setResult(ButtonType.OK);
                            alert.hide();
                        }
                    }));
                    idlestage.setCycleCount(1);
                    idlestage.play();
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        provideCustomerID(usId);
                    }


                });
                userName.clear();
                userPassword.clear();
                userAddress.clear();
                userPhNo.clear();
                userCNIC.clear();


            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Caution!");
                    alert.setContentText("Oops ! Looks like some entered Credentials are not a number. Please Try Again ");
                    alert.showAndWait();
                });

                userName.clear();
                userPassword.clear();
                userAddress.clear();
                userPhNo.clear();
                userCNIC.clear();
            }
        }
    }

    @FXML
    protected void back(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void owner_Man(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ownman.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("ownMan.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void setChoice(ActionEvent actionEvent) {
        if (radioBtn1.isSelected()) {
            owner.setVisible(true);
            manager.setVisible(false);
            loginMan.setVisible(false);
            loginOwn.setVisible(true);
        } else if (radioBtn2.isSelected()) {
            manager.setVisible(true);
            owner.setVisible(false);
            loginOwn.setVisible(false);
            loginMan.setVisible(true);
        }
    }

    @FXML
    protected void guestController(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("guestMenu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("styleMenu.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void ownerController(ActionEvent actionEvent) throws IOException {
        String own_Id = idOwn.getText();
        String own_Pass = password.getText();
        String own_Name = ownName.getText();
        Db_Connection loginconn = new Db_Connection();

        if (own_Id.isEmpty() || own_Pass.isEmpty() || own_Name.isEmpty()) {
            loginEmptyCredentialsDialog();

        } else {
            if (checkInt(idOwn)) {
                boolean valid = loginconn.checkOwnerLoginCredentials( parseInt(idOwn.getText()),own_Name ,  own_Pass);

                if (valid && parseInt(idOwn.getText())==124) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("owner1.fxml"));
                    root = loader.load();
                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                }else  if(valid && parseInt(idOwn.getText())==125) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("owner2.fxml"));
                    root = loader.load();
                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    loginDenialDialog();
                    ownName.clear();
                    password.clear();
                    idOwn.clear();
                }

            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Caution!");
                    alert.setContentText("Oops ! Looks like entered Id Is not a number. Please Try Again ");
                    alert.showAndWait();
                });
                ownName.clear();
                password.clear();
                idOwn.clear();
            }
        }
    }

    @FXML
    protected void exit() {
        Platform.exit();
    }

    @FXML
    protected void localCarsSection(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("localCarsSection.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("localSection.css");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void importedCarsSection(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("importedCarsSection.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("importedSection.css");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void loginDenialDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setContentText("Invalid Credentials. Try Again ");
            alert.showAndWait();
        });

    }

    @FXML
    protected void loginSuccessDialog() throws SQLException {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Welcome " + userLoginName.getText());
            alert.setContentText("Signed in Successfully");
            alert.showAndWait();
        });
        addCustIdtoDb();

    }

    @FXML
    protected void loginEmptyCredentialsDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("Please fill the required Credentials");
            alert.showAndWait();
        });

    }

    @FXML
    private boolean checkInt(TextField cusId) {
        try {
            Integer.parseInt(cusId.getText());
            return true;
        } catch (NumberFormatException e) {

            return false;
        }
    }

    @FXML
    protected void registerEmptyCredentialsDialog() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Message");
            alert.setContentText("Please fill the required Credentials");
            alert.showAndWait();
        });

    }

    @FXML
    protected void provideCustomerID(int cusId) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer ID");
            alert.setContentText("Your Customer ID is:\n\n " + cusId);
            alert.showAndWait();

        });

    }

    @FXML
    protected void manLogin(ActionEvent actionEvent) throws IOException {
        String own_Id = ownId.getText();
        String man_Id = manId.getText();
        String man_Name = name.getText();
        String man_age= age.getText();
        String man_phNo = phNo.getText();

        Db_Connection loginconn = new Db_Connection();

        if (own_Id.isEmpty() || man_Id.isEmpty() || man_phNo.isEmpty() || man_age.isEmpty() || man_Name.isEmpty()) {
            loginEmptyCredentialsDialog();

        } else {
            if (checkInt(ownId) && checkInt(manId) && checkInt(age) && checkInt(phNo)) {
                boolean valid = loginconn.checkManagerLoginCredentials( parseInt(own_Id), parseInt(man_Id));

                if (valid && parseInt(own_Id)==124 ) {
                    root = FXMLLoader.load(getClass().getResource("manLogin.fxml"));
                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    scene.getStylesheets().add("man.css");
                    stage.setScene(scene);
                    stage.show();

                }else  if(valid && parseInt(ownId.getText())==125 ) {
                    root = FXMLLoader.load(getClass().getResource("manLoginImported.fxml"));
                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    scene.getStylesheets().add("man.css");
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    loginDenialDialog();
                    ownId.clear();
                    name.clear();
                    manId.clear();
                    phNo.clear();
                    age.clear();
                }

            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Caution!");
                    alert.setContentText("Oops ! Looks like entered Id Is not a number. Please Try Again ");
                    alert.showAndWait();
                });
                ownId.clear();
                name.clear();
                manId.clear();
                phNo.clear();
                age.clear();
            }
        }
    }

    @FXML
    protected void addNewLocalCarsController(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("localNewCarAdd.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("man1.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void addOldLocalCarsController(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("localOldCarAdd.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("man2.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void addNewImportCarsController(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("importNewCarAdd.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("man1.css");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void addOldImportCarsController(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("importOldCarAdd.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("man2.css");
        stage.setScene(scene);
        stage.show();

    }

//    @FXML
//    protected void exit() {
//        Platform.exit();
//    }

    public void addCustIdtoDb() throws SQLException {
        if (!customerLoginId.getText().isEmpty()) {
            Connection connection = Db_Connection.provideConnection();
            int costmerid = parseInt(customerLoginId.getText());
            String sql = "SELECT * FROM CUSTOMER WHERE CUST_ID = '" + costmerid + "'";

            Statement statement = connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery(sql);
            while (resultSet1.next()) {

                int custid = resultSet1.getInt(1);

                String sqlTbl = "INSERT INTO CUSTID (CUST_ID)";
                String sqlVal = "VALUES ('" +custid+ "')";
                String sql1 = sqlTbl + sqlVal;
                Statement statement1 = connection.createStatement();
                int rows = statement1.executeUpdate(sql1);
                if (rows > 0) {
                    statement1.close();
                    // connection.close();
                }
            }

        } else {
            System.out.println(customerLoginId.getText() + "is null");
        }
    }
}
