package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MedicalRecordsController {

    @FXML private TableColumn<?, ?> diagnosisColm;
    @FXML private Label greetingLabel;
    @FXML private TableColumn<?, ?> id;
    @FXML private Button insertBtn;
    @FXML private TableView<?> medicalRecordsTable;
    @FXML private TableColumn<?, ?> patientID;
    @FXML private TableColumn<?, ?> prescriptionColm;
    @FXML private TableColumn<?, ?> recordDateColm;
    @FXML private TextField txtDiagnosis;
    @FXML private TextField txtPatientID;
    @FXML private TextField txtPrescription;
    @FXML private DatePicker txtRecordDate;
    
    private Stage stage;
	private Scene scene;
	private Parent root;

	public void switchToDoctorAppointments(ActionEvent event) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorDashboard.fxml"));
		root = loader.load();
		
		// loads the methods of other class.
		sceneController3 sceneController3 = loader.getController();
		sceneController3.setGreeting(Session.getFullname()); // get session.
		sceneController3.loadAppointments(null); 
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("Doctor Appointments");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

    public void switchToMedicalRecords(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorDashboard2.fxml"));
	    root = loader.load();

	    // Get the new controller instance
	    MedicalRecordsController controller = loader.getController();
	    controller.setGreeting(Session.getFullname());

	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setTitle("Doctor's Medical Records");
	    stage.setScene(scene);
	    stage.centerOnScreen();
	    stage.show();
	}

    public void switchToScene1(ActionEvent event) throws IOException{
    	Session.clear(); // clears the session / log out.
		root = FXMLLoader.load(getClass().getResource("MainDashboard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("MedAssistant");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

	public void setGreeting(String fullname) {
		    greetingLabel.setText("Good day, Dr. " + fullname);
	}

}

