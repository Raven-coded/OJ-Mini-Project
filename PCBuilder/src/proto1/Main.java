package proto1;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {
	
	private double xOffset = 0; 
	private double yOffset = 0;
    //Stage window;
	
	@Override
	public void start(Stage window) 
	{
		try 
		{
			window.initStyle(StageStyle.UNDECORATED);
			Parent root = FXMLLoader.load(getClass().getResource("/proto1/login_page.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.show();
			window.setResizable(false);
	
			System.out.println("check commit");
			
			//To Move the window
			scene.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            }
	        });
			
			scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	window.setX(event.getScreenX() - xOffset);
	            	window.setY(event.getScreenY() - yOffset);
	            }
	        });
			
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void connectToDB() 
	{
		Connection connection = null;
		try 
		{
			// create a database connection
			
			connection = DriverManager.getConnection("jdbc:sqlite:UserData.db");
			System.out.println("Connected in main!");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			String personTableQueryString = "create table if not exists personTable (" + "firstName string,"
					+ "lastName string, userName string primary key," + "email string, password string)";
			statement.executeUpdate(personTableQueryString);
			
			String cartTableQuery = "create table if not exists cartTable (" + "username string,"
					+ "name string," + "price float,"+ "foreign key (username) references personTable(username))";
			statement.executeUpdate(cartTableQuery);
			
			String orderedQuery = "create table if not exists orderedTable (" + "username string,"
					+ "Name string,"  + "price float," + "foreign key (username) references personTable(username))";
			statement.executeUpdate(orderedQuery);

			Statement statementPaymentTable = connection.createStatement();
			String paymentTableQuery = "create table if not exists paymentTable (" + "username string," + "paymentType string,"
					+ "creditNumber string," + "expireDateMM int," + "expireDateYY int," + "firstname string," + "lastname string,"
					+ "billingAddress1 string," + "billingAddress2 string," + "city string," + "state string," + "zipcode int," + "phone string)";
			statementPaymentTable.executeUpdate(paymentTableQuery);


		} 
		
		catch (SQLException e) 
		{
			// if the error message is "out of memory",
			// it probably means no database file is found
			System.err.println(e.getMessage());
		} 
		
		finally 
		{
			try 
			{
				if (connection != null)
					connection.close();
			} 
			
			catch (SQLException e) 
			{
				// connection close failed.
				System.err.println(e);
			}
		}
	}

	public static void main(String[] args) 
	{

		connectToDB();
		launch(args);
	}
}
