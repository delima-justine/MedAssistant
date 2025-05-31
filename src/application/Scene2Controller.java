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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Scene2Controller implements Initializable {
	
	@FXML TextField emailTextField;
	@FXML TextField passwordTextField;
	@FXML private ComboBox<String> roleSelectionLogin; 
	
	String[] roles = {"Patient", "Staff", "Admin", "Doctor"};
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		roleSelectionLogin.getItems().addAll(roles);
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
		
	public void login(ActionEvent event) throws IOException{
		
		String user_email = emailTextField.getText(); 
		String user_password = passwordTextField.getText();
		String user_role = roleSelectionLogin.getValue();
		String fxml_scene = null;
		String scene_title = null;
		
		// Checks if the user selects a role.
		if (user_role == null || user_role.isEmpty()) {
			Alert alertDialog = new Alert(Alert.AlertType.ERROR);
//			alertDialog.setTitle("Warning");
			alertDialog.setHeaderText("Error");
			alertDialog.setContentText("Please choose a role.");
			alertDialog.showAndWait();
			return; // return if empty.
		} else {
		
			switch (user_role) {
				case "Patient":
					fxml_scene = "patientDashboard.fxml";
					scene_title = "Patient Dashboard";
					break;
				case "Doctor":
					fxml_scene = "doctorDashboard.fxml";
					scene_title = "Doctor Dashboard";
					break;
				case "Admin":
					fxml_scene = "adminDashboard.fxml";
					scene_title = "Admin Dashboard";
					break;
				case "Staff":
					fxml_scene = "staffDashboard.fxml";
					scene_title = "Staff Dashboard";
					break;
			}	
		}
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_scene));
		root = loader.load();
		
		sceneController3 sceneController3 = loader.getController();
		sceneController3.displayName(user_email, user_password, user_role);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle(scene_title);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	public void register(ActionEvent event) {
		String user_role = roleSelectionLogin.getValue();
//		String fxml_scene = null;
		
		// Checks if the user selects a role.
		if (user_role == null || user_role.isEmpty()) {
			Alert alertDialog = new Alert(Alert.AlertType.ERROR);
//			alertDialog.setTitle("Warning");
			alertDialog.setHeaderText("Error");
			alertDialog.setContentText("Please choose a role.");
			alertDialog.showAndWait();
			return; // return if empty.
		} else {
			Alert alertDialog = new Alert(Alert.AlertType.CONFIRMATION);
			alertDialog.setTitle("Account confirmation");
			alertDialog.setHeaderText("Confirm");
			alertDialog.setContentText("Are the information correct?");
			alertDialog.showAndWait();
			return; // return if empty.
		}
	}
}
