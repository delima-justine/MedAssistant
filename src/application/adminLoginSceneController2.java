package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class adminLoginSceneController2 {

	@FXML
	Label adminEmailLabel;
	@FXML
	Label adminPasswordLabel;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void displayAdminInformation(String email, String password) {
		adminEmailLabel.setText("Email: " + email);
		adminPasswordLabel.setText("Password: " + password);
	}
	
	public void switchToScene1(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("MedAssistant");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
}
