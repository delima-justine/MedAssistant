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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PatientSceneController3 implements Initializable {

	@FXML private TableView<UserMedicalRecord> medicalRecordTable;
	 @FXML private TableColumn<UserMedicalRecord, String> doctorNameColm;
    @FXML private TableColumn<UserMedicalRecord, String> diagnosisColm;
    @FXML private TableColumn<UserMedicalRecord, String> prescriptionColm;
    
    private Stage stage;
   	private Scene scene;
   	private Parent root;

    public void switchToEditProfile(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("patientDashboard.fxml"));
	    root = loader.load();

	    PatientSceneController controller = loader.getController();
	    try {
			controller.loadInformation(null);
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
    
    public void switchToViewAppointments(ActionEvent event) throws IOException, SQLException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("patientDashboard2.fxml"));
	    root = loader.load();
	    
	    PatientSceneController2 controller = loader.getController();
	    controller.loadPatientAppointments(null);

	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setTitle("Staff Dashboard");
	    stage.setScene(scene);
	    stage.centerOnScreen();
	    stage.show();
    }

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
    
    public void loadMedicalRecords(ActionEvent event) throws SQLException {
    	ObservableList<UserMedicalRecord> medicalRecordsList = FXCollections.observableArrayList();
    	
    	try(Connection conn = MedAssistantDB.getConnection()) {
    		Statement stmt = conn.createStatement();
    		String sql = "SELECT "
    				+ "Appointments.doctor_name, "
    				+ "CONVERT(varchar(MAX), DecryptByPassPhrase('MyKey', MedicalRecords.diagnosis)) AS diagnosis, "
    				+ "CONVERT(varchar(MAX), DecryptByPassPhrase('MyKey', MedicalRecords.prescription)) AS prescription "
    				+ "FROM Appointments "
    				+ "INNER JOIN MedicalRecords ON "
    				+ "Appointments.patient_id=MedicalRecords.patient_id "
    				+ "WHERE Appointments.patient_id = " + Session.getPatientId() + ";";
    			
    		ResultSet rs = stmt.executeQuery(sql);
    		
    		while(rs.next()) {
    			UserMedicalRecord newRecord = new UserMedicalRecord(
    					rs.getString("doctor_name"),
    					rs.getString("diagnosis"),
    					rs.getString("prescription")
    				);
    			medicalRecordsList.add(newRecord);
    		}
    		medicalRecordTable.setItems(medicalRecordsList);
    	} catch(SQLException e) {
    		new Alert(Alert.AlertType.ERROR, "Medical record not found.").show();
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		doctorNameColm.setCellValueFactory(new PropertyValueFactory<UserMedicalRecord, String>("doctorName"));
		diagnosisColm.setCellValueFactory(new PropertyValueFactory<UserMedicalRecord, String>("diagnosis"));
		prescriptionColm.setCellValueFactory(new PropertyValueFactory<UserMedicalRecord, String>("prescription"));
		
		try {
			loadMedicalRecords(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

