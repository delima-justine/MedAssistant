package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class sceneController3 implements Initializable {
	
	@FXML private Label emailLabel;
	@FXML private Label passwordLabel;
	@FXML private Label roleSelectionLabel;
	@FXML private ListView<String> managePatientsTable;
	@FXML private TableView<Patient> appointmentTable;
	@FXML private TableColumn<Patient, String> id;
    @FXML private TableColumn<Patient, String> patientID;
    @FXML private TableColumn<Patient, String> doctorName;
	@FXML private TableColumn<Patient, String> appointmentDate;
	@FXML private TableColumn<Patient, String> status;
	@FXML private TextField txtPatientID;
	@FXML private TextField txtDoctorName;
	@FXML private DatePicker txtApptDate;
	@FXML private ComboBox<String> selectStatus;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	String[] appointmentStatus = {"Active", "Pending", "Cancelled"};
	
	public void displayName(String email, String password, String role) {		
		
		emailLabel.setText("Email: " + email);
		passwordLabel.setText("Password: " + password);
		roleSelectionLabel.setText("Role: " + role);
//		System.out.println("Role: " + role);
	}
	
	// Method to insert an appointment.
	public void insertAppointment(ActionEvent event) {
		String patient_id = txtPatientID.getText().trim();
		String doctor_name = txtDoctorName.getText().trim();
		LocalDate appt_date = txtApptDate.getValue();
		String appt_status = selectStatus.getValue();
		 	
		if (!txtPatientID.getText().isEmpty() || !txtDoctorName.getText().isEmpty() || 
			!txtDoctorName.getText().isEmpty() || !(txtApptDate.getValue() == null) ||
			!(selectStatus.getValue() == null)) {
				Patient newPatient= new Patient("", patient_id, doctor_name, appt_date, appt_status);
				
				appointmentTable.getItems().add(newPatient);
				txtPatientID.clear();
				txtDoctorName.clear();
				txtApptDate.setValue(null);
				selectStatus.setValue(selectStatus.getPromptText());
		} else {
			new Alert(Alert.AlertType.INFORMATION, "Please fill all the fields.").show();
		}
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
	
	ObservableList<Patient> initialData() {
		Patient p1 = new Patient("1", "P0001", "Franco", LocalDate.now(), "Active");
		Patient p2 = new Patient("2", "P0002", "JC", LocalDate.now(), "Incoming");
		Patient p3 = new Patient("3", "P0002", "Patricia", LocalDate.now(), "Incoming");
		return FXCollections.<Patient>observableArrayList(p1,p2, p3);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		id.setCellValueFactory(new PropertyValueFactory<Patient, String>("id"));
		patientID.setCellValueFactory(new PropertyValueFactory<Patient, String>("patientID"));
		doctorName.setCellValueFactory(new PropertyValueFactory<Patient, String>("doctorName"));
		appointmentDate.setCellValueFactory(new PropertyValueFactory<Patient, String>("appointmentDate"));
		status.setCellValueFactory(new PropertyValueFactory<Patient, String>("status"));
		
		appointmentTable.setItems(initialData());
		
		selectStatus.getItems().addAll(appointmentStatus);
	}
}
