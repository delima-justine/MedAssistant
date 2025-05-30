package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class sceneController3 implements Initializable {
	
	@FXML private Label emailLabel;
	@FXML private Label passwordLabel;
	@FXML private Label roleSelectionLabel;
	@FXML private ListView<String> managePatientsTable;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	String[] patientNames = {"Justine", "PJ", "JM", "Bea", "Pat", "Kath"};
	
	public void displayName(String email, String password, String role) {		
		
		emailLabel.setText("Email: " + email);
		passwordLabel.setText("Password: " + password);
		roleSelectionLabel.setText("Role: " + role);
//		System.out.println("Role: " + role);
	}
	
	public void switchToScene1(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("MainDashboard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("MedAssistant");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		managePatientsTable.getItems().addAll(patientNames);
	}
}
