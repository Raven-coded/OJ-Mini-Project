package proto1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.Date;

public class TransactionController {

	private String username;
	@FXML
	private TextField creditNumField;

	@FXML
	private TextField firstnameField;

	@FXML
	private TextField lastnameField;

	@FXML
	private TextField monthField;

	@FXML
	private TextField yearField;

	@FXML
	private TextField billingAddress1Field;

	@FXML
	private TextField billingAddress2Field;

	@FXML
	private TextField cityField;

	@FXML
	private TextField stateField;

	@FXML
	private TextField zipField;

	@FXML
	private TextField phoneField;

	@FXML
	private RadioButton visaRadio;

	@FXML
	private RadioButton masterCardRadio;

	@FXML
	private RadioButton amexRadio;

	@FXML
	private RadioButton discoveryRadio;

	@FXML
	private Button payButton;

	@FXML
	private Button cancelButton;

	private String cardType;

	public void showAlert(String alertString) {
		Alert errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setTitle("Form Error!");
		errorAlert.setHeaderText(alertString);
		errorAlert.show();
	}

	public void selectVisaRadio(ActionEvent event) {
		masterCardRadio.setSelected(false);
		amexRadio.setSelected(false);
		discoveryRadio.setSelected(false);
		cardType = "Visa";
		return;
	}

	public void selectMasterRadio(ActionEvent event) {
		visaRadio.setSelected(false);
		amexRadio.setSelected(false);
		discoveryRadio.setSelected(false);
		cardType = "MasterCard";
		return;
	}

	public void selectAmericanRadio(ActionEvent event) {
		visaRadio.setSelected(false);
		masterCardRadio.setSelected(false);
		discoveryRadio.setSelected(false);
		cardType = "AMex";
		return;
	}

	public void selectDiscoveryRadio(ActionEvent event) {
		visaRadio.setSelected(false);
		masterCardRadio.setSelected(false);
		amexRadio.setSelected(false);
		cardType = "Discovery";
		return;
	}

	@FXML
	public void pay(ActionEvent event) throws IOException {
		String creditString = creditNumField.getText();
		if (creditString.isEmpty()) {
			showAlert("Please enter your credit card numbers");
			return;
		} else if (creditString.length() != 16 || !creditString.matches("[0-9]+")) {
			showAlert("Your card credit card number must be 16 digits" + " and cannot contain letter");
			return;
		}
		if ((monthField.getText().isEmpty() || yearField.getText().isEmpty())) {
			showAlert("Please enter your month and date");
			return;
		}
		if ((monthField.getText().length() != 2) || !monthField.getText().matches("0[1-9]|1[012]")) {
			showAlert("Please enter the month in the format of mm " + "for example: 02 or 11");
			return;
		}
		if ((yearField.getText().length() != 2 || !yearField.getText().matches("1[8-9]|[2-9][1-9]"))) {
			showAlert("Please enter the year in the format of yy and it must be year of 18 or after 18 "
					+ "for example: 18 or 22");
			return;
		}
		if ((monthField.getText().isEmpty() || yearField.getText().isEmpty())) {
			showAlert("Please enter your month and date");
			return;
		}
		if (firstnameField.getText().isEmpty()) {
			showAlert("Please enter your first name");
			return;
		}
		if (lastnameField.getText().isEmpty()) {
			showAlert("Please enter your last name");
			return;
		}
		if (billingAddress1Field.getText().isEmpty()) {
			showAlert("Please enter your billing address");
			return;
		}
		if (cityField.getText().isEmpty()) {
			showAlert("Please enter your city");
			return;
		}
		if (stateField.getText().isEmpty()) {
			showAlert("Please enter your state");
			return;
		}
		if (zipField.getText().isEmpty()) {
			showAlert("Please enter your zipcode");
			return;
		}
		if (phoneField.getText().isEmpty()) {
			showAlert("Please enter your phone");
			return;
		}

		CreditCard card = new CreditCard();
		card.setCardType(cardType);
		card.setBillingAddress1Field(billingAddress1Field.getText());
		card.setBillingAddress2Field(billingAddress2Field.getText());
		card.setCityField(cityField.getText());
		card.setCreditNumField(creditNumField.getText());
		card.setMonthField(Integer.parseInt(monthField.getText()));
		card.setYearField(Integer.parseInt(yearField.getText()));
		card.setFirstnameField(firstnameField.getText());
		card.setLastnameField(lastnameField.getText());
		card.setPhoneField(phoneField.getText());
		card.setStateField(stateField.getText());
		card.setZipField(Integer.parseInt(zipField.getText()));
		card.setUsername(username);

		if (saveCreditCard(card)) {
			Alert alert = new Alert(AlertType.NONE, "Order placed Successfully!", ButtonType.YES);
    		alert.showAndWait();
			
			FXMLLoader CatalogLoader = new FXMLLoader(getClass().getResource("Catalog.fxml"));
			Parent Catalog = (Parent) CatalogLoader.load();
			Scene registrationScene = new Scene(Catalog);
			Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

			CatalogController username1 = CatalogLoader.getController();
			username1.sendData(username);

			window.setScene(registrationScene);
			window.show();
			
		} else {
			Alert Confirmation = new Alert(Alert.AlertType.CONFIRMATION);
			Confirmation.setTitle("Payment failed");
			Confirmation.setHeaderText("Transaction Fail");
			return;
		}
	}

	@FXML
	public void CancelButtontoLogin(ActionEvent event) throws IOException {
		FXMLLoader GraphicPage = new FXMLLoader(getClass().getResource("Catalog1.fxml"));
		Parent GraphicParent = (Parent) GraphicPage.load();
		Scene GraphicScene = new Scene(GraphicParent);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

		CatalogController newusername = GraphicPage.getController();
		newusername.sendData(username);
		window.setScene(GraphicScene);
		window.show();
		
		
	}

	public void sendData(String username) {
		this.username = username;
		System.out.println("potato " + username);
	}

	private boolean saveCreditCard(CreditCard card) {
		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:UserData.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 sec.

			String getAllCredit = "insert into paymentTable values('" + card.getUsername() + "', '" + card.getCardType()
					+ "', '" + card.getCreditNumField() + "', '" + card.getMonthField() + "', '" + card.getYearField()
					+ "', '" + card.getFirstnameField() + "', '" + card.getLastnameField() + "', '"
					+ card.getBillingAddress1Field() + "', '" + card.getBillingAddress2Field() + "', '"
					+ card.getCityField() + "', '" + card.getStateField() + "', '" + card.getZipField() + "', '"
					+ card.getPhoneField() + "')";

			statement.executeUpdate(getAllCredit);

			String query = "select * from cartTable where username = '" + this.username + "'";

			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				GraphicCardParts existedpart = new GraphicCardParts(rs.getString("Name"), rs.getFloat("Price"));

				String orderedAddQuery = "insert into orderedTable values('" + username + "', '" +  existedpart.getName() + "', '"  + existedpart.getPrice() + "')";
				statement.executeUpdate(orderedAddQuery);

				String removeCart = "delete from cartTable where " + "username ='" + username + "' and " +  "Name='" + existedpart.getName() + "' and " + "price='" + existedpart.getPrice() + "'";
				statement.executeUpdate(removeCart);
			}
			statement.close();
			rs.close();

			return true;
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
		return true;
	}
}