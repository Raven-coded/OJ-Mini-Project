package proto1;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController {
		private User user;
	    
	    @FXML
	    private JFXTextField firstName ;
	    
	    @FXML
	    private JFXTextField lastName ;
	    
	    @FXML
	    private JFXTextField userName ;
	    
	    @FXML
	    private JFXTextField email ;

	    @FXML
	    private TextField password ;
	    
	    @FXML
	    private PasswordField confirm ;
	    
	    @FXML
	    private JFXButton Create;
	    
	    @FXML
	    private Button Cancel;

	    @FXML
	    public void Create(ActionEvent event) {
	    	Create.setOnAction(new EventHandler<ActionEvent>() {
	    	    @Override
	    	    public void handle(ActionEvent event) {

	    	    }
	    	});
	    }
	    
	    @FXML
	    public void showAlert(String alertString) {
	    	Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	    	errorAlert.setTitle("Form Error!");
	    	errorAlert.setHeaderText(alertString);
	    	errorAlert.show();
	    }
	    
		@FXML 
		public void createButtonClick(ActionEvent event) throws IOException, NoSuchAlgorithmException {
			
			
			if(firstName.getText().isEmpty()) { 
	            showAlert("Please enter your first name");
	            return;
	        }
			
			if(lastName.getText().isEmpty()) {
	            showAlert("Please enter your last name");
	            return;
	        }
			
			if(userName.getText().isEmpty()) {
	            showAlert("Please enter your username");
	            return;
			}
			
			if(email.getText().isEmpty()) {
	            showAlert("Please enter your email");
	            return;
	        }

			if(password.getText().isEmpty()) {
	            showAlert("Please enter your password");
	            return;
	        }

			if(confirm.getText().isEmpty()) {
	            showAlert("Please enter your confirm password");
	            return;
	        }
			
			if (!password.getText().equals(confirm.getText())) {
				showAlert("Your password and confirm password don't match");
 	            return;
 	        }
			
			User newUser = new User();
			newUser.firstName = firstName.getText();
			newUser.lastName = lastName.getText();
			newUser.userName = userName.getText();
			newUser.email = email.getText();
			newUser.password = Security.hashPassword(password.getText());
			
			User existedUser = getUserInDB(userName.getText());
			if (existedUser == null) {
				boolean isSavedSuccessfully = saveUserToDB(newUser);
				
				if (isSavedSuccessfully) {
					Alert alert = new Alert(AlertType.NONE, "Registration Succesful! Welcome to PC Builder", ButtonType.OK);
					alert.showAndWait();
					if (alert.getResult() == ButtonType.OK) {

						FXMLLoader CatalogLoader = new FXMLLoader(getClass().getResource("Catalog1.fxml"));
						Parent Catalog = (Parent) CatalogLoader.load();
						Scene registrationScene = new Scene(Catalog);
						Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

						CatalogController username = CatalogLoader.getController();
						username.sendData(userName.getText());

						window.setScene(registrationScene);
						window.show();
					}
				} else {
		    		Alert alert = new Alert(AlertType.ERROR, "Registration Failed!", ButtonType.YES);
		    		alert.show();
		    	}
			}
			else {
	    		Alert alert = new Alert(AlertType.ERROR, "Please enter different username!", ButtonType.YES);
	    		alert.show();
	    	}
			
		}
			
		private boolean saveUserToDB(User user) {
	    	Connection connection = null;
	        try
	        {
	          // create a database connection
	        	
	          connection = DriverManager.getConnection("jdbc:sqlite:UserData.db");
	          System.out.println("Connected in regcon1!");
	          Statement statement = connection.createStatement();
	          statement.setQueryTimeout(30);  // set timeout to 30 sec.
	          
	          
	          String getAllPersonString = "insert into personTable values('"
	        		    + user.firstName + "', '"
		          		+ user.lastName  + "', '"
		          		+ user.userName  + "', '"
		          		+ user.email     + "', '"
		          		+ user.password  + "')";
	          statement.executeUpdate(getAllPersonString);
	          return true;
	        }
	        catch(SQLException e)
	        {
	          // if the error message is "out of memory",
	          // it probably means no database file is found
	          System.err.println(e.getMessage());
	        }
	        finally
	        {
	          try
	          {
	            if(connection != null)
	              connection.close();
	          }
	          catch(SQLException e)
	          {
	            // connection close failed.
	            System.err.println(e);
	          }
	        }
	        return true;
	    }
		
		private User getUserInDB(String userName) {
			Connection connection = null;
			try {
				// create a database connection
				
				connection = DriverManager.getConnection("jdbc:sqlite:UserData.db");
				System.out.println("Connected in regcon2!");
				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30); // set timeout to 30 sec.

				String getAllPersonString = "select * from personTable where userName = '" + userName + "'";

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
		public void CancelButtontoLogin(ActionEvent event) throws IOException{
			Parent Login = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene LoginScene = new Scene(Login);
			Stage window2 = (Stage) ((Node) (event.getSource())).getScene().getWindow();
			window2.setScene(LoginScene);
			window2.show();
	    }
		
		 @FXML
		    private JFXButton close;
		    
		    @FXML
		    public void closebutton() {
		        Stage stage = (Stage) close.getScene().getWindow();
		        stage.close();
		    }
}

