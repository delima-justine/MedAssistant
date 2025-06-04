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
	@FXML private Label greetingLabel;
	@FXML private ListView<String> managePatientsTable;
	@FXML private TableView<Appointment> appointmentTable;
	@FXML private TableColumn<Appointment, Integer> id;
    @FXML private TableColumn<Appointment, String> patientID;
    @FXML private TableColumn<Appointment, String> doctorName;
	@FXML private TableColumn<Appointment, String> appointmentDate;
	@FXML private TableColumn<Appointment, String> status;
	@FXML private TextField txtPatientID;
	@FXML private DatePicker txtApptDate;
	@FXML private ComboBox<String> selectStatus;
    @FXML private TextField txtDiagnosis;
    @FXML private TextField txtPrescription;
    @FXML private DatePicker txtRecordDate;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String fullname;
	
	String[] appointmentStatus = {"Done", "Active", "Pending", "Cancelled"};
	
	public void setGreeting(String fullname) throws SQLException {	
		this.fullname = fullname;
		System.out.println(fullname);
		greetingLabel.setText("Good day, Dr. " + Session.getFullname());
		loadAppointments(null); // load information.
	}
	
	// Method to insert an appointment.
	public void insertAppointment(ActionEvent event) throws SQLException {
		String patient_id = txtPatientID.getText().trim();
		LocalDate appt_date = txtApptDate.getValue();
		String appt_status = selectStatus.getValue();
		 
		// Checks if appointment fields are empty
		if (!txtPatientID.getText().isEmpty() && 
			!(appt_date == null) && 
			!(appt_status == null)) {
				try (Connection conn = MedAssistantDB.getConnection()){
					Statement stmt = conn.createStatement();
					String sql = "INSERT INTO Appointments"
							+ "(patient_id, doctor_name, appointment_date, status)"
							+ "VALUES('"
							+ patient_id + "', '"
							+ fullname + "', '"
							+ java.sql.Date.valueOf(appt_date) + "', '"
							+ appt_status + "')";
					stmt.executeUpdate(sql);
//					Appointment newAppointment= new Appointment(null, patient_id, doctor_name, appt_date, appt_status);
//					appointmentTable.getItems().add(newAppointment); - for front end temporary displayed.
					loadAppointments(null); // to update the table view automatically.
				} catch(SQLException e) {
					e.printStackTrace();
				}

				txtPatientID.clear();
				txtApptDate.setValue(null);
				selectStatus.setValue(null);
		} else {
			new Alert(Alert.AlertType.INFORMATION, "Please fill all the fields.").show();
		}
	}
	
	public void loadAppointments(ActionEvent event) throws SQLException {
		ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
		
		try(Connection conn = MedAssistantDB.getConnection()) {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM Appointments WHERE doctor_name = '" + Session.getFullname() + "';";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("Success Fetch.");
			
			while(rs.next()) {
				Appointment newAppointment = new Appointment(
						rs.getInt("id"), 
						rs.getString("patient_id"), 
						rs.getString("doctor_name"), 
						rs.getDate("appointment_date").toLocalDate(), 
						rs.getString("status")
						);
					appointmentList.add(newAppointment); 
				}
				// Adds the appointmentList to the table.
				appointmentTable.setItems(appointmentList);
			} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Method to update an appointment
	public void updateAppointment(ActionEvent event) throws SQLException {
		Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
		
		if (selectedAppointment == null) {	
			new Alert(Alert.AlertType.ERROR, "Please select a row to update").show();
			return;
		}
		
		try (Connection conn = MedAssistantDB.getConnection()) {
			Statement stmt = conn.createStatement();
			String sql = "UPDATE Appointments "
					+ "SET patient_id = '" + txtPatientID.getText() + "', "
					+ "appointment_date = '" + java.sql.Date.valueOf(txtApptDate.getValue()) + "', "
					+ "status = '" + selectStatus.getValue() + "'"
					+ "WHERE id = " + selectedAppointment.getId() + ";";	
			
			stmt.executeUpdate(sql); // executes the query.
			loadAppointments(null); // refreshes the table.
		} catch(SQLException e) {
			new Alert(Alert.AlertType.ERROR, "Database Error").show();
		} catch(NullPointerException e) {
			new Alert(Alert.AlertType.ERROR, "Please select a row to update").show();
		}
	}
	
	// Puts a row to the text fields.
	public void editAppointment(ActionEvent event) {
		Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
		
		if (selectedAppointment == null) {	
			new Alert(Alert.AlertType.ERROR, "Please select a row to update").show();
			return;
		}
		
		txtPatientID.setText(selectedAppointment.getPatientID());
		txtApptDate.setValue(selectedAppointment.getAppointmentDate());
		selectStatus.setValue(selectedAppointment.getStatus());
	}
	
	// Method to delete an appointment
	public void deleteAppointment(ActionEvent event) throws SQLException {
		// Variable that gets the selected row from the table view.
		Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

		try (Connection conn = MedAssistantDB.getConnection()) {
		    if (selectedAppointment != null) {
		    	Integer appointment_id = selectedAppointment.getId();
		    	Statement stmt = conn.createStatement();
		    	
		    	String sql = "DELETE FROM Appointments WHERE id = " + appointment_id;
		    	stmt.executeUpdate(sql);
		    	loadAppointments(null); // updates table view automatically.
		    } else {
		        new Alert(Alert.AlertType.WARNING, "Please select an appointment to delete.").show();
		    }
		} catch (SQLException e) {
			new Alert(Alert.AlertType.ERROR, "Database Error").show();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		id.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
		patientID.setCellValueFactory(new PropertyValueFactory<Appointment, String>("patientID"));
		doctorName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("doctorName"));
		appointmentDate.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentDate"));
		status.setCellValueFactory(new PropertyValueFactory<Appointment, String>("status"));
		
		try {
			loadAppointments(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		selectStatus.getItems().addAll(appointmentStatus);
	}
	
	public void switchToScene1(ActionEvent event) throws IOException{
		new Alert(Alert.AlertType.INFORMATION, "Logged out.").show();
		Session.clear(); // clears the session / log out.
		root = FXMLLoader.load(getClass().getResource("MainDashboard.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setTitle("MedAssistant");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	public void switchToMedicalRecords(ActionEvent event) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorDashboard2.fxml"));
	    root = loader.load();

	    MedicalRecordsController controller = loader.getController();
	    controller.setGreeting(Session.getFullname());
	    controller.loadMedicalRecords(null);

	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setTitle("Doctor's Medical Records");
	    stage.setScene(scene);
	    stage.centerOnScreen();
	    stage.show();
	}
}
