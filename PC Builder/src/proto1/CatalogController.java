package proto1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CatalogController implements Initializable {

	ObservableList<GraphicCardParts> data = FXCollections.observableArrayList();
	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	private Label label;

	@FXML
	TableView<GraphicCardParts> table;

	@FXML
	private TableColumn<?, ?> Id;

	@FXML
	private TableColumn<?, ?> Name;

	@FXML
	private TableColumn<?, ?> Memory;

	@FXML
	private TableColumn<?, ?> Chipset;

	@FXML
	private TableColumn<?, ?> Speed;

	@FXML
	private TableColumn<?, ?> Price;

	@FXML
	private Button addButton;

	@FXML
	private Button viewButton;

	@FXML
	private String username;

	@FXML
	private Button graphics;

	@FXML
	private Button proces;

	@FXML
	private Button cooler;

	@FXML
	private Button fan;

	@FXML
	private Button power;

	@FXML
	private Button mother;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("In initialize\n");
		Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
		Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		Memory.setCellValueFactory(new PropertyValueFactory<>("Memory"));
		Chipset.setCellValueFactory(new PropertyValueFactory<>("Chipset"));
		Speed.setCellValueFactory(new PropertyValueFactory<>("Speed"));
		Price.setCellValueFactory(new PropertyValueFactory<>("Price"));

	}

	public void addToCartButton() {
		// table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
		if (table.getSelectionModel().getSelectedItem() != null) {
			GraphicCardParts selectedGCard = table.getSelectionModel().getSelectedItem();
			if (saveToCart(selectedGCard)) {
				Alert alert = new Alert(AlertType.NONE, selectedGCard.getName() + " Added Successfully!",
						ButtonType.OK);
				alert.showAndWait();
				return;
			} else {
				Alert alert = new Alert(AlertType.NONE, "Graphics Card Adding Failed!", ButtonType.OK);
				alert.showAndWait();
				return;
			}
		} else {
			Alert alert = new Alert(AlertType.NONE, "Please Select Graphic Card To Add!", ButtonType.OK);
			alert.showAndWait();
			return;
		}

	}

	private boolean saveToCart(GraphicCardParts cart) {
		Connection connection = null;
		try {
			// create a database connection
			System.out.println("Connected in savetocart!");
			connection = DriverManager.getConnection("jdbc:sqlite:UserData.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 second
			// check on commit

			String addToCart = "insert into cartTable values('" + username + "', '" + cart.getName() + "', '"
					+ cart.getPrice() + "')";
			statement.executeUpdate(addToCart);
			return true;
		} catch (SQLException e) {
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
		return true;
	}

	public void loadDatabase() {

		Connection connection = null;
		try {
			// create a database connection
			// Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:PcBuilder.db");
			System.out.println("Connected in Controller\n");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.
			String query = "select * from GraphicCard";
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				GraphicCardParts grapar = new GraphicCardParts(rs.getInt("Id"), rs.getString("Name"),
						rs.getString("Memory"), rs.getString("Chipset"), rs.getInt("Speed_GHz"), rs.getFloat("Price"));

				data.add(grapar);
				table.setItems(data);
			}
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

	public CatalogController() {

	}

	public void ShoppingCartButton(ActionEvent event) throws IOException {
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

	@FXML
	private JFXButton close;

	@FXML
	public void closebutton() {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
	}

	public void sendData(String text) {
		this.username = text;

		loadDatabase();
	}

	// Scene Changers
	public void cGraphicCard(ActionEvent event) throws IOException {
		FXMLLoader GraphicPage = new FXMLLoader(getClass().getResource("Catalog1.fxml"));
		Parent GraphicParent = (Parent) GraphicPage.load();
		Scene GraphicScene = new Scene(GraphicParent);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

		CatalogController newusername = GraphicPage.getController();
		newusername.sendData(username);
		window.setScene(GraphicScene);
		window.show();

		// movement
		GraphicScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		GraphicScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				window.setX(event.getScreenX() - xOffset);
				window.setY(event.getScreenY() - yOffset);
			}
		});
	}

	public void cProcessor(ActionEvent event) throws IOException {
		FXMLLoader ProcessorPage = new FXMLLoader(getClass().getResource("Catalog2.fxml"));
		Parent ProcessorParent = (Parent) ProcessorPage.load();
		Scene ProcessorScene = new Scene(ProcessorParent);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

		CatalogController1 newusername = ProcessorPage.getController();
		newusername.sendData(username);
		window.setScene(ProcessorScene);
		window.show();

		// movement
		ProcessorScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		ProcessorScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				window.setX(event.getScreenX() - xOffset);
				window.setY(event.getScreenY() - yOffset);
			}
		});
	}

	public void cCooler(ActionEvent event) throws IOException {
		FXMLLoader CoolerPage = new FXMLLoader(getClass().getResource("Catalog3.fxml"));
		Parent CoolerParent = (Parent) CoolerPage.load();
		Scene CoolerScene = new Scene(CoolerParent);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

		CatalogController2 newusername = CoolerPage.getController();
		newusername.sendData(username);
		window.setScene(CoolerScene);
		window.show();

		// movement
		CoolerScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		CoolerScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				window.setX(event.getScreenX() - xOffset);
				window.setY(event.getScreenY() - yOffset);
			}
		});
	}

	public void cFan(ActionEvent event) throws IOException {
		FXMLLoader FanPage = new FXMLLoader(getClass().getResource("Catalog4.fxml"));
		Parent FanParent = (Parent) FanPage.load();
		Scene FanScene = new Scene(FanParent);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

		CatalogController3 newusername = FanPage.getController();
		newusername.sendData(username);
		window.setScene(FanScene);
		window.show();

		// movement
		FanScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		FanScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				window.setX(event.getScreenX() - xOffset);
				window.setY(event.getScreenY() - yOffset);
			}
		});
	}

	public void cPower(ActionEvent event) throws IOException {
		FXMLLoader PowerPage = new FXMLLoader(getClass().getResource("Catalog5.fxml"));
		Parent PowerParent = (Parent) PowerPage.load();
		Scene PowerScene = new Scene(PowerParent);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

		CatalogController4 newusername = PowerPage.getController();
		newusername.sendData(username);
		window.setScene(PowerScene);
		window.show();

		// movement
		PowerScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		PowerScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				window.setX(event.getScreenX() - xOffset);
				window.setY(event.getScreenY() - yOffset);
			}
		});
	}

	public void cMother(ActionEvent event) throws IOException {
		FXMLLoader MotherPage = new FXMLLoader(getClass().getResource("Catalog6.fxml"));
		Parent MotherParent = (Parent) MotherPage.load();
		Scene MotherScene = new Scene(MotherParent);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

		CatalogController5 newusername = MotherPage.getController();
		newusername.sendData(username);
		window.setScene(MotherScene);
		window.show();

		// movement
		MotherScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		MotherScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				window.setX(event.getScreenX() - xOffset);
				window.setY(event.getScreenY() - yOffset);
			}
		});
	}

}