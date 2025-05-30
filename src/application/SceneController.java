package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	// METHODS
	public void switchToScene1(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("MainDashboard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("MedAssistant");
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
}
