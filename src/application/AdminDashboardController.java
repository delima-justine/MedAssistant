package application;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminDashboardController implements Initializable {

    @FXML private Label emailLabel;
    @FXML private Label passwordLabel;
    @FXML private ComboBox<String> roleSelectionCmbox;
    @FXML private Label roleSelectionLabel;
    @FXML private TextField txtUsername;
    @FXML private TableView<User> UsersTable;
    @FXML private TableColumn<User, Integer> userId;
    @FXML private TableColumn<User, String> userRoleColm;
    @FXML private TableColumn<User, String> usernameColm;
    
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
    
    ObservableList<User> initialData() {
    	User user1 = new User(1, "John Carlo", "Patient");
    	User user2 = new User(2, "Patricia Anne", "Patient");
    	return FXCollections.<User> observableArrayList(user1, user2);
    }
    
    public void deleteUser(ActionEvent event) {
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initializes the columns
		userId.setCellValueFactory(new PropertyValueFactory<User, Integer>("userId"));
		usernameColm.setCellValueFactory(new PropertyValueFactory <User, String>("username"));
		userRoleColm.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
		
		UsersTable.setItems(initialData()); // Adds the initial data
		
		roleSelectionCmbox.getItems().addAll(roles);
	}

}

