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

public class adminLoginSceneController {
	
	@FXML
	TextField adminEmailTextField;
	@FXML
	TextField adminPasswordTextField;
	
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
	
	public void adminLogin(ActionEvent event) throws IOException{
		
		String admin_email = adminEmailTextField.getText();
		String admin_password = adminPasswordTextField.getText();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("adminDashboard.fxml")); // doctor dashboard
		root = loader.load();
		
		adminLoginSceneController2 adminLoginSceneController2 = loader.getController();
		adminLoginSceneController2.displayAdminInformation(admin_email, admin_password);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Admin & Staff Dashboard");
		stage.centerOnScreen();
		stage.setScene(scene);
		stage.show();
	}
}
