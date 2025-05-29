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

public class doctorLoginSceneController2 {
	
	@FXML
	Label doctorEmailLabel;
	@FXML
	Label doctorPasswordLabel;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void displayDoctorInformation(String email, String password) {
		doctorEmailLabel.setText("Email: " + email);
		doctorPasswordLabel.setText("Password: " + password);
	}
	
	public void switchToScene1(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("MainDashboard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("First Scene");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
}
