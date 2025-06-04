package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MedicalRecordsController implements Initializable{

    @FXML private Label greetingLabel;
    @FXML private TableView<MedicalRecord> medicalRecordsTable;
    @FXML private TableColumn<MedicalRecord, Integer> medicalId;
    @FXML private TableColumn<MedicalRecord, String> patientIdColm;
    @FXML private TableColumn<MedicalRecord, String> diagnosisColm;
    @FXML private TableColumn<MedicalRecord, String> prescriptionColm;
    @FXML private TableColumn<MedicalRecord, String> recordDateColm;
    @FXML private TextField txtDiagnosis;
    @FXML private TextField txtPatientID;
    @FXML private TextField txtPrescription;
    @FXML private DatePicker txtRecordDate;
    
    private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void setGreeting(String fullname) throws SQLException {
	    greetingLabel.setText("Good day, Dr. " + fullname);
	    loadMedicalRecords(null);
	}

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

    public void switchToMedicalRecords(ActionEvent event) throws IOException, SQLException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorDashboard2.fxml"));
	    root = loader.load();

	    // Get the new controller instance
	    MedicalRecordsController controller = loader.getController();
	    controller.setGreeting(Session.getFullname());
	    loadMedicalRecords(null); // load medical records from database to tableview

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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 														Dapat same ito nong nasa MedicalRecord class getter and setter.            
		medicalId.setCellValueFactory(new PropertyValueFactory<MedicalRecord, Integer>("medicalId"));
		patientIdColm.setCellValueFactory(new PropertyValueFactory<MedicalRecord, String>("patientId"));
		diagnosisColm.setCellValueFactory(new PropertyValueFactory<MedicalRecord, String>("diagnosis"));
		prescriptionColm.setCellValueFactory(new PropertyValueFactory<MedicalRecord, String>("prescription"));
		recordDateColm.setCellValueFactory(new PropertyValueFactory<MedicalRecord, String>("recordDate"));
		
		try {
			loadMedicalRecords(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMedicalRecords(ActionEvent event) throws SQLException {
		ObservableList<MedicalRecord> medicalRecordsList = FXCollections.observableArrayList();
		
		try(Connection conn = MedAssistantDB.getConnection()) {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM MedicalRecords;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				MedicalRecord newRecord = new MedicalRecord(
						rs.getInt("id"),
						rs.getString("patient_id"),
						rs.getString("diagnosis"),
						rs.getString("prescription"),
						rs.getDate("record_date").toLocalDate()
				);
				medicalRecordsList.add(newRecord);
			}
			medicalRecordsTable.setItems(medicalRecordsList);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void addMedicalRecord(ActionEvent event) throws SQLException {
		String patientId = txtPatientID.getText().trim();
		String doctor_diagnosis = txtDiagnosis.getText().trim();
		String doctor_prescription = txtPrescription.getText().trim();
		LocalDate record_date = txtRecordDate.getValue();
		
		// Add the record to the table view.aa
		try(Connection conn = MedAssistantDB.getConnection()) {
			if (!patientId.isEmpty() && 
				!doctor_diagnosis.isEmpty() && 
				!doctor_prescription.isEmpty() && 
				record_date != null) 
			{
				Statement stmt = conn.createStatement();
				String sql = "INSERT INTO MedicalRecords"
						+ "(patient_id, diagnosis, prescription, record_date)"
						+ "VALUES('"
						+ patientId + "', '"
						+ doctor_diagnosis + "', '" 
						+ doctor_prescription + "', '"
						+ java.sql.Date.valueOf(record_date) + "'); ";
//				medicalRecordsTable.getItems().add(newRecord);
				stmt.executeUpdate(sql);
				loadMedicalRecords(null);
			} else {
				new Alert(Alert.AlertType.WARNING, "Please fill all the fields.").show();
			}
		} catch(SQLException e) {
			new Alert(Alert.AlertType.ERROR, "Database Error.").show();
			e.printStackTrace();
		}
		// Clears the field.
		txtPatientID.clear();
		txtDiagnosis.clear();
		txtPrescription.clear();
		txtRecordDate.setValue(null);
	}
	
	public void updateMedicalRecord(ActionEvent event) throws SQLException {
		MedicalRecord selectedMedicalRecord = medicalRecordsTable.getSelectionModel().getSelectedItem();
		
		if (selectedMedicalRecord == null) {
			new Alert(Alert.AlertType.WARNING, "Please select a row to edit.");
			return;
		}
		
		try(Connection conn = MedAssistantDB.getConnection()) {
			Statement stmt = conn.createStatement();
			String sql = "UPDATE MedicalRecords "
					+ "SET patient_id = '" + txtPatientID.getText() + "', "
					+ "diagnosis = '" + txtDiagnosis.getText() + "', "
					+ "prescription = '" + txtPrescription.getText() +"', "
					+ "record_date = '" + java.sql.Date.valueOf(txtRecordDate.getValue()) + "'"
					+ "WHERE id = " + selectedMedicalRecord.getMedicalId() + ";";
			
			stmt.executeUpdate(sql);
			loadMedicalRecords(null);
		} catch(SQLException e) {
			new Alert(Alert.AlertType.ERROR, "Patient not found.").show();
		}

		// Refresh the table
		medicalRecordsTable.refresh();
	}
	
	// Method to edit a medical record.
	public void editMedicalRecord(ActionEvent event) {
		MedicalRecord selectedMedicalRecord = medicalRecordsTable.getSelectionModel().getSelectedItem();
		
		if (selectedMedicalRecord == null) {
			new Alert(Alert.AlertType.WARNING, "Please select a row to edit.");
			return;
		} 
		
		txtPatientID.setText(selectedMedicalRecord.getPatientId());
		txtDiagnosis.setText(selectedMedicalRecord.getDiagnosis());
		txtPrescription.setText(selectedMedicalRecord.getPrescription());
		txtRecordDate.setValue(selectedMedicalRecord.getRecordDate());
	}
	
	public void deleteMedicalRecord(ActionEvent event) throws SQLException {
		MedicalRecord selectedMedicalRecord = medicalRecordsTable.getSelectionModel().getSelectedItem();
		
		try (Connection conn = MedAssistantDB.getConnection()) {
			if (selectedMedicalRecord != null) {
				Integer medicalRecordId = selectedMedicalRecord.getMedicalId();
				Statement stmt = conn.createStatement();
				String sql = "DELETE FROM MedicalRecords WHERE id = " + medicalRecordId;
				
				stmt.execute(sql);
				loadMedicalRecords(null);
				
//				medicalRecordsTable.getItems().remove(selectedMedicalRecord);
			} else {
				new Alert(Alert.AlertType.WARNING, "Please select a row to delete.").show();
			}
		} catch (SQLException e) {
			new Alert(Alert.AlertType.ERROR, "Database Error").show();
		}
	}

}

