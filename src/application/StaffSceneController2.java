package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StaffSceneController2 implements Initializable{

    @FXML private TableColumn<Patient, String> appointmentDateColm;
    @FXML private TableColumn<Patient, Integer> appointmentIdColm;
    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, Integer> patientIdColm;
    @FXML private TableColumn<Patient, String> patientNameColm;
    @FXML private TableColumn<Patient, String> statusColm;
    @FXML private Label greetingLabel;
    
    private Stage stage;
   	private Scene scene;
   	private Parent root;


    public void switchToScene1(ActionEvent event) throws IOException {
    	new Alert(Alert.AlertType.INFORMATION, "Logged out.").showAndWait();
    	Session.clear(); // clears the session / log out.
		root = FXMLLoader.load(getClass().getResource("MainDashboard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("MedAssistant");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
    }
    
    public void switchToAppointments(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("staffDashboard.fxml"));
	    root = loader.load();

	    StaffSceneController controller = loader.getController();
	    try {
			controller.loadAppointments(null); // loads appointments to staff table
		} catch (SQLException e) {
			e.printStackTrace();
		}

	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setTitle("Staff Dashboard");
	    stage.setScene(scene);
	    stage.centerOnScreen();
	    stage.show();
    }

    public void loadPatients(ActionEvent event) throws SQLException {
    	ObservableList<Patient> patientList = FXCollections.observableArrayList();
    	
    	try(Connection conn = MedAssistantDB.getConnection()) {
    		Statement stmt = conn.createStatement();
    		String sql = "SELECT "
    				+ "Appointments.appointment_id, "
    				+ "Appointments.patient_id, "
    				+ "CONVERT(varchar(MAX), DecryptByPassPhrase('MyKey', Patients.name)) as name, "
    				+ "Appointments.appointment_date, "
    				+ "Appointments.status\r\n"
    				+ "FROM Appointments\r\n"
    				+ "INNER JOIN Patients ON Appointments.patient_id=Patients.patient_id;";
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		while(rs.next()) {
    			Patient newPatient = new Patient(
    					rs.getInt("appointment_id"),
    					rs.getInt("patient_id"),
    					rs.getString("name"),
    					rs.getDate("appointment_date").toLocalDate(),
    					rs.getString("status")
    				);
    			patientList.add(newPatient);
    		}
    		patientsTable.setItems(patientList);
    	} catch(Exception e) {
    		new Alert(Alert.AlertType.ERROR, "Error Occured");
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		appointmentIdColm.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("appointmentId"));
		patientIdColm.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("patientId"));
		patientNameColm.setCellValueFactory(new PropertyValueFactory<Patient, String>("patientName"));
		appointmentDateColm.setCellValueFactory(new PropertyValueFactory<Patient, String>("appointmentDate"));
		statusColm.setCellValueFactory(new PropertyValueFactory<Patient, String>("appointmentStatus"));
	
		try {
			loadPatients(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

