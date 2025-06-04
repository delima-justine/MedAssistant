package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientSceneController implements Initializable {

    @FXML private DatePicker calendarBirthdate;
    @FXML private Label emailLabel;
    @FXML private Label passwordLabel;
    @FXML private Label roleSelectionLabel;
    @FXML private TextField textAge;
    @FXML private TextField textBarangay;
    @FXML private TextField textCity;
    @FXML private TextField textContactNumber;
    @FXML private TextField textEmail;
    @FXML private TextField textFirstname;
    @FXML private TextField textHouseNumber;
    @FXML private TextField textLastName;
    @FXML private TextField textPassword;
    @FXML private TextField textStreet;
    @FXML private TextField textUsername;
    
    private Stage stage;
   	private Scene scene;
   	private Parent root;

    public void switchToScene1(ActionEvent event) throws IOException {
    	new Alert(Alert.AlertType.INFORMATION, "Logged out.").showAndWait();
    	Session.clear(); // clears the session / log out.
		root = FXMLLoader.load(getClass().getResource("MainDashboard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("MedAssistant");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
    }
    
    public void loadInformation(ActionEvent event) throws SQLException {
    	try(Connection conn = MedAssistantDB.getConnection()) {
    		Statement stmt = conn.createStatement();
    		String sql = "SELECT username, email, password, first_name, "
    				+ "last_name, age, birthdate, house_number, street, "
    				+ "barangay, city, contact_number FROM UsersInformation "
    				+ "WHERE email = '" + Session.getEmail() + "';"; 
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		while(rs.next()) {
    			textUsername.setText(rs.getString("username"));
    			textEmail.setText(rs.getString("email"));
    			textPassword.setText(rs.getString("password"));
    			textFirstname.setText(rs.getString("first_name"));
    			textLastName.setText(rs.getString("last_name"));
    			textAge.setText(rs.getString("age"));
    			calendarBirthdate.setValue(rs.getDate("birthdate").toLocalDate());
    			textHouseNumber.setText(rs.getString("house_number"));
    			textStreet.setText(rs.getString("street"));
    			textBarangay.setText(rs.getString("barangay"));
    			textCity.setText(rs.getString("city"));
    			textContactNumber.setText(rs.getString("contact_number"));
    		}
    	} catch(SQLException e) {
    		new Alert(Alert.AlertType.ERROR, "Fetch Error from database.");
    	}
    }
    
    public void saveInformation(ActionEvent event) {
    	try(Connection conn = MedAssistantDB.getConnection()) {
    		Integer updatedAge = Integer.parseInt(textAge.getText()); 
    		Statement stmt = conn.createStatement();
    		String sql = "UPDATE UsersInformation "
    				+ "SET username = '" + textUsername.getText() +"', "
    				+ "email = '" + textEmail.getText() + "', "
    				+ "password = '" + textPassword.getText() + "', "
    				+ "first_name = '" + textFirstname.getText() + "', "
    				+ "last_name = '" + textLastName.getText() + "', "
    				+ "age = '" + updatedAge + "', "
    				+ "birthdate = '" + java.sql.Date.valueOf(calendarBirthdate.getValue()) + "', "
    				+ "house_number = '" + textHouseNumber.getText() + "', "
    				+ "street = '" + textStreet.getText() + "', "
    				+ "barangay = '" + textBarangay.getText() + "', "
    				+ "city = '" + textCity.getText() + "', "
    				+ "contact_number = '" + textContactNumber.getText() + "' " 
    				+ "WHERE email = '" + Session.getEmail() + "';"; 
    		stmt.executeUpdate(sql);
    		new Alert(Alert.AlertType.INFORMATION, "Information Updated.").show();
    	} catch(SQLException e) {
    		new Alert(Alert.AlertType.ERROR, "Database Update Error.");
    	} catch(Exception e ) {
    		new Alert(Alert.AlertType.ERROR, "Please enter a valid age");
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			loadInformation(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

