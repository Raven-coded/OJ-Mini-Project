package proto1;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;


public class LoginController {
	
	private double xOffset = 0; 
	private double yOffset = 0;
	@FXML
	private TextField userField;

	@FXML
	private PasswordField passField;
	
	 @FXML
	 private JFXTextField l_username;
	    
	 @FXML
	 private JFXPasswordField l_password;

	@FXML
	private void login() {

	}

	private User getUserInDB(String userName, String password) {
		Connection connection = null;
		try {
			// create a database connection
		
			connection = DriverManager.getConnection("jdbc:sqlite:UserData.db");
			Statement statement = connection.createStatement();
			System.out.println("Connected in logincontroller!");
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			String getAllPersonString = "select * from personTable where userName = '" + userName + "' and password = '"
					+ password + "'";

			ResultSet rs = statement.executeQuery(getAllPersonString);
			while (rs.next()) {
				User existedUser = new User();
				existedUser.firstName = rs.getString("firstName");
				existedUser.lastName = rs.getString("lastName");
				existedUser.userName = rs.getString("userName");
				existedUser.email = rs.getString("email");
				existedUser.password = rs.getString("password");

				return existedUser;
				// read the result set
				// System.out.println("name = " + rs.getString("firstName"));
				// System.out.println("personId = " + rs.getInt("personId"));
			}

		} catch (SQLException e) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
		return null;
	}

	@FXML
	public void Login (ActionEvent event) throws IOException, NoSuchAlgorithmException 
	{
		String password = Security.hashPassword(passField.getText());
		User existedUser = getUserInDB(userField.getText(), password);
		if (existedUser != null) {

			Alert alert = new Alert(AlertType.NONE, "Login Succesful! Welcome to PC Builder Application", ButtonType.OK);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {
				FXMLLoader CatalogLoader = new FXMLLoader(getClass().getResource("Catalog1.fxml"));
				Parent Catalog = (Parent) CatalogLoader.load();
				//Parent root = FXMLLoader.load(getClass().getResource("Catalog1.fxml"));
				Scene CatalogScene = new Scene(Catalog);
				//Scene registrationScene = new Scene(root);
				Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();
				window.setScene(CatalogScene);			
				window.show();

				CatalogController username = CatalogLoader.getController();
				username.sendData(userField.getText());

				window.setScene(CatalogScene);
				window.show();
				
				CatalogScene.setOnMousePressed(new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent event) {
		                xOffset = event.getSceneX();
		                yOffset = event.getSceneY();
		            }
		        });
				
				
				CatalogScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent event) {
		            	window.setX(event.getScreenX() - xOffset);
		            	window.setY(event.getScreenY() - yOffset);
		            }
		        });
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Login Failed!", ButtonType.YES);
			alert.show();
		}

	}

	@FXML
	public void Create(ActionEvent event) throws IOException {
		Parent Registration = FXMLLoader.load(getClass().getResource("signup_page.fxml"));
		Scene RegistrationScene = new Scene(Registration);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		window.setScene(RegistrationScene);
		window.show();
		
		RegistrationScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
		
		
		RegistrationScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	window.setX(event.getScreenX() - xOffset);
            	window.setY(event.getScreenY() - yOffset);
            }
        });
	}
	
	 @FXML
	    private JFXButton close;
	    
	    @FXML
	    public void closebutton() {
	        Stage stage = (Stage) close.getScene().getWindow();
	        stage.close();
	    }
}
