package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Scene2Controller {
	
	@FXML
	Label emailLabel;
	@FXML
	Label passwordLabel;
	
	public void displayName(String email, String password) {
		emailLabel.setText("Email: " + email);
		passwordLabel.setText("Password: " + password);
//		System.out.println(email);
//		System.out.print(password);
	}
}
