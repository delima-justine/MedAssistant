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

public class doctorLoginSceneController {
	
	@FXML
	TextField doctorEmailTextField;
	@FXML
	TextField doctorPasswordTextField;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchToScene1(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("MainDashboard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("MedAssistant");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	public void doctorLogin(ActionEvent event) throws IOException{
		
		String doctor_email = doctorEmailTextField.getText();
		String doctor_password = doctorPasswordTextField.getText();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorDashboard.fxml")); // doctor dashboard
		root = loader.load();
		
		doctorLoginSceneController2 doctorLoginSceneController2 = loader.getController();
		doctorLoginSceneController2.displayDoctorInformation(doctor_email, doctor_password);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Doctor Dashboard");
		stage.setScene(scene);
		stage.show();
	}
}
