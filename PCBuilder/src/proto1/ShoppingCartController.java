package proto1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShoppingCartController implements Initializable {
	ObservableList<GraphicCardParts> data = FXCollections.observableArrayList();
	private double xOffset = 0;
	private double yOffset = 0;


	@FXML
	TableView<GraphicCardParts> table;

	@FXML
	private TableColumn<?, ?> Name;

	@FXML
	private TableColumn<?, ?> Price;

	@FXML
	private Label NameLabel;

	@FXML
	private Label priceLabel;

	@FXML
	private Label subtotalLabel;

	@FXML
	private Label shippingLabel;

	@FXML
	private Label totalLabel;

	@FXML
	private Label taxLabel;

	private String username;
	
	

	private float totalPrice = 0f;
	private final float tax = 0.03f;
	public ShoppingCartController() {

	}

	public void sendData(String username) {
		this.username = username;
		System.out.println("VERIFY " + this.username);
		loadDatabase();
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
		table.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// GraphicCardParts selectedpart = table.getSelectionModel().getSelectedItem();
				// showContentDetails(selectedBook);
			}
		});
	}

	public void showContentDetails(GraphicCardParts p) {
		NameLabel.setText(p.getName());
		priceLabel.setText("Rs." + p.getPrice());
	}

	public void loadDatabase() {

		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:UserData.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 second
			String query = "select * from cartTable where username = '" + this.username + "'";
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				GraphicCardParts existedpart = new GraphicCardParts(rs.getString("Name"), rs.getFloat("Price"));
				totalPrice += existedpart.getPrice();
				data.add(existedpart);
				table.setItems(data);
			}
			
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			String taxAmount = decimalFormat.format(totalPrice * tax);
			String totalAmount = decimalFormat.format(totalPrice + (totalPrice * tax));
			String total = decimalFormat.format(totalPrice);
			taxLabel.setText(("Rs." + String.valueOf(taxAmount)));
			totalLabel.setText("Rs." + String.valueOf(totalAmount));
			subtotalLabel.setText("Rs." + String.valueOf(total));
			
			
			
			statement.close();
			rs.close();
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
	}

	public void removeButton(ActionEvent event) throws IOException {
		GraphicCardParts selectedpart = table.getSelectionModel().getSelectedItem();
		if (table.getSelectionModel().getSelectedItem() != null) {
			if (removeSelected(selectedpart)) {
				Alert alert = new Alert(AlertType.NONE, "Removed Successfully!", ButtonType.OK);
				alert.showAndWait();
				FXMLLoader shoppingCartPage = new FXMLLoader(getClass().getResource("Cart_page.fxml"));
				Parent shoppingCartParent = (Parent) shoppingCartPage.load();
				Scene shoppingCartScene = new Scene(shoppingCartParent);
				Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();
				ShoppingCartController newUserName = shoppingCartPage.getController();
				newUserName.sendData(username);
				window.setScene(shoppingCartScene);
				window.show();
				
				// movement
				shoppingCartScene.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						xOffset = event.getSceneX();
						yOffset = event.getSceneY();
					}
				});

				shoppingCartScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						window.setX(event.getScreenX() - xOffset);
						window.setY(event.getScreenY() - yOffset);
					}
				});
			}
			else if (event == null) {
				Alert alert = new Alert(AlertType.NONE, "Removal Failed!", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			else {
				Alert alert = new Alert(AlertType.NONE, "Removal Failed!", ButtonType.OK);
				alert.showAndWait();
				return;
			}
		}
		else {
			Alert alert = new Alert(AlertType.NONE, "Please select a Part for removal!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
	}

	public void backButton(ActionEvent event) throws IOException {

		FXMLLoader CatalogPage = new FXMLLoader(getClass().getResource("Catalog1.fxml"));
		Parent CatalogParent = (Parent) CatalogPage.load();
		Scene CatalogScene = new Scene(CatalogParent);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

		CatalogController newUserName = CatalogPage.getController();
		newUserName.sendData(username);
		window.setScene(CatalogScene);
		window.show();
		
		// movement
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

	private boolean removeSelected(GraphicCardParts cart) {

    	Connection connection = null;
        try
        {
          // create a database connection
          connection = DriverManager.getConnection("jdbc:sqlite:UserData.db");
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.
          //check on commit
          
          String removeCart = "delete from cartTable where "
          			+ "username ='" + username + "' and "
	          		+ "Name='" + cart.getName()  + "' and "
	          		+ "price='" +cart.getPrice() +  "'";
          statement.executeUpdate(removeCart);
          statement.close();
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

	@FXML
	public void CheckoutButton(ActionEvent event) throws IOException {
		FXMLLoader creditCardPage = new FXMLLoader(getClass().getResource("CreditCardTransaction.fxml"));
		Parent creditCardParent = (Parent) creditCardPage.load();
		Scene creditCardScene = new Scene(creditCardParent);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

		TransactionController newUserName = creditCardPage.getController();
		newUserName.sendData(username);
		window.setScene(creditCardScene);
		window.show();
		
		// movement
		creditCardScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		creditCardScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
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
