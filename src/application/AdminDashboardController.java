package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminDashboardController implements Initializable {

    @FXML private Label emailLabel;
    @FXML private Label passwordLabel;
    @FXML private ComboBox<String> roleSelectionComboBox;
    @FXML private Label roleSelectionLabel;
    @FXML private TextField txtUsername;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPassword;
    @FXML private TextField txtContact;
    @FXML private DatePicker birthdatePicker;
    @FXML private TableView<User> UsersTable;
    @FXML private TableColumn<User, Integer> userId;
    @FXML private TableColumn<User, String> userEmailColm;
    @FXML private TableColumn<User, String> userPasswordColm;
    @FXML private TableColumn<User, String> userRoleColm;
    @FXML private TableColumn<User, String> usernameColm;
    @FXML private TableColumn<User, String> firstnameColm;
    @FXML private TableColumn<User, String> lastnameColm;
    @FXML private TableColumn<User, String> Birthdate;
    @FXML private TableColumn<User, String> contactColm;
    
    private Stage stage;
   	private Scene scene;
   	private Parent root;
   	String[] roles = {"Patient", "Staff", "Admin", "Doctor"};

   	// Log out method
    @FXML void switchToScene1(ActionEvent event) throws IOException {
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
    
    public void loadUsers(ActionEvent event) throws SQLException {
    	ObservableList<User> userList = FXCollections.observableArrayList();  
    	
    	try(Connection conn = MedAssistantDB.getConnection()) {
    		Statement stmt = conn.createStatement();
    		String sql = "SELECT id, username, first_name, "
    				+ "last_name, email, password, birthdate, contact_number, role "
    				+ "FROM UsersInformation;";
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		while(rs.next()) {
    			User newUser = new User(
    					rs.getInt("id"),
    					rs.getString("username"),
    					rs.getString("first_name"),
    					rs.getString("last_name"),
    					rs.getString("email"),
    					rs.getString("password"),
    					rs.getDate("birthdate").toLocalDate(),
    					rs.getString("contact_number"),
    					rs.getString("role")
    			);
    			userList.add(newUser);
    		}
    		UsersTable.setItems(userList);
    	} catch(SQLException e) {
    		new Alert(Alert.AlertType.WARNING, "Database Error.").show();
    	}
    }
    
    // Adds new user to the table
    public void addUser(ActionEvent event) throws SQLException {
    	String newUsername = txtUsername.getText().trim();
    	String newFirstName = txtFirstName.getText().trim();
    	String newLastName = txtLastName.getText().trim();
    	String newEmail = txtEmail.getText().trim();
    	String newPassword = txtPassword.getText().trim();
    	String newContact = txtContact.getText().trim();
    	LocalDate newBirthdate = birthdatePicker.getValue();
    	String newRole = roleSelectionComboBox.getValue();
    	
    	if (!txtUsername.getText().isEmpty() &&  
    		!txtEmail.getText().isEmpty() && 
    		!txtPassword.getText().isEmpty() &&
    		!(newRole == null)) {
	    		try(Connection conn = MedAssistantDB.getConnection()) {
	        		Statement stmt = conn.createStatement();
	        		String sql = "INSERT INTO UsersInformation(username, first_name, "
	        				+ "last_name, email, password, birthdate, role, contact_number)"
	        				+ "VALUES('"
	        				+ newUsername + "', '"
	        				+ newFirstName + "', '"
	        				+ newLastName + "', '"
	        				+ newEmail + "', '"
	        				+ newPassword + "', '"
	        				+ java.sql.Date.valueOf(newBirthdate) + "', '"
	        				+ newRole + "', '"
	        				+ newContact + "');";
	        		stmt.executeUpdate(sql);
	        		loadUsers(null);
	        		
	        		if(newRole == "Patient") {
	        			sql = "INSERT INTO Patients(name, contact_number)"
		                		+ "VALUES('"
		                		+ newFirstName + " " + newLastName + "', '"
		                		+ newContact + "');";
		                	stmt.executeUpdate(sql);
	        		} else {
	        			return;
	        		}
	        		
	        		txtUsername.clear();
	            	txtEmail.clear();
	            	txtPassword.clear();
	            	roleSelectionComboBox.setValue(null);
	        	} catch(SQLException e) {
	        		new Alert(Alert.AlertType.ERROR, "Database Error.").show();
	        	}
    	} else {
    		new Alert(Alert.AlertType.WARNING, "Please fill all the fields.").show();
    	}
    }
    
    public void updateUser(ActionEvent event) throws SQLException {
    	User selectedUser = UsersTable.getSelectionModel().getSelectedItem();
    	
    	if (selectedUser == null) {
    		new Alert(Alert.AlertType.WARNING, "Please select a user.").show();
    		return;
    	}
    	
    	try(Connection conn = MedAssistantDB.getConnection()) {
	    		Statement stmt = conn.createStatement();
	    		String sql = "UPDATE UsersInformation "
	    					+ "SET username = '" + txtUsername.getText() + "', "
	    					+ "first_name = '" + txtFirstName.getText() + "', "
	    					+ "last_name = '" + txtLastName.getText() + "', "
	    					+ "email = '" + txtEmail.getText() + "', "
	    					+ "password = '" + txtPassword.getText() + "', "
	    					+ "birthdate = '" + java.sql.Date.valueOf(birthdatePicker.getValue()) +"', "
	    					+ "role = '" + roleSelectionComboBox.getValue() + "' "
	    					+ "contact_number = '" + txtContact.getText() + "'"
	    					+ "WHERE id = " + selectedUser.getUserId() + ";";
	    		stmt.executeUpdate(sql);
	    		loadUsers(null);
	    	} catch(SQLException e) {
	    		new Alert(Alert.AlertType.ERROR, "Database Error").show();
    	}
    	UsersTable.refresh();
    }
    
    public void editUser(ActionEvent event) {
    	User selectedUser = UsersTable.getSelectionModel().getSelectedItem();
    	
    	if (selectedUser != null) {
    		txtUsername.setText(selectedUser.getUsername());
    		txtFirstName.setText(selectedUser.getUserFirstName());
    		txtLastName.setText(selectedUser.getUserLastName());
    		txtEmail.setText(selectedUser.getUserEmail());
    		txtPassword.setText(selectedUser.getUserPassword());
    		roleSelectionComboBox.setValue(selectedUser.getRole());
    	} else {
    		new Alert(Alert.AlertType.WARNING, "Please select a user.").show();
    	}
    }
    
    public void deleteUser(ActionEvent event) throws SQLException {
    	User selectedUser = UsersTable.getSelectionModel().getSelectedItem();
    	
    	try (Connection conn = MedAssistantDB.getConnection()) {
    		if (selectedUser != null) {
    			Integer selectedUserId = selectedUser.getUserId(); 
    			Statement stmt = conn.createStatement();
        		String sql = "DELETE FROM UsersInformation WHERE id = " + selectedUserId;
        		
        		stmt.executeUpdate(sql); // executes the query
        		loadUsers(null);
//	    		UsersTable.getItems().remove(selectedUser);
	    	} else {
	    		new Alert(Alert.AlertType.WARNING, "Please select a user.").show();
	    	}
    	} catch(SQLException e) {
    		new Alert(Alert.AlertType.ERROR, "Database Error");
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initializes the columns
		userId.setCellValueFactory(new PropertyValueFactory<User, Integer>("userId"));
		usernameColm.setCellValueFactory(new PropertyValueFactory <User, String>("username"));
		userRoleColm.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
		userEmailColm.setCellValueFactory(new PropertyValueFactory<User, String>("userEmail"));
		userPasswordColm.setCellValueFactory(new PropertyValueFactory<User, String>("userPassword"));
		firstnameColm.setCellValueFactory(new PropertyValueFactory<User, String>("userFirstName"));
		lastnameColm.setCellValueFactory(new PropertyValueFactory<User, String>("userLastName"));
		Birthdate.setCellValueFactory(new PropertyValueFactory<User, String>("userBirthdate"));
		contactColm.setCellValueFactory(new PropertyValueFactory<User, String>("userBirthdate"));
		
//		UsersTable.setItems(initialData()); // Adds the initial data
		try {
			loadUsers(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		roleSelectionComboBox.getItems().addAll(roles);
	}

}

