package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SceneController {
	
	@FXML
	TextField emailTextField;
	@FXML
	TextField passwordTextField;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	// METHODS
	public void switchToScene1(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("First Scene");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	public void switchToPatientSelection(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("patientSelection.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Patient");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	public void switchToLoginForm(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("loginForm.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	public void switchToRegisterForm(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("regForm.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Register");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	public void switchToDoctorLoginForm(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("doctorLoginForm.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Doctor Login");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	public void login(ActionEvent event) throws IOException{
		
		String user_email = emailTextField.getText();
		String user_password = passwordTextField.getText();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("sampleDashboard.fxml"));
		root = loader.load();
		
		Scene2Controller scene2Controller = loader.getController();
		scene2Controller.displayName(user_email, user_password);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("First Scene");
		stage.setScene(scene);
		stage.show();
	}
	
}
