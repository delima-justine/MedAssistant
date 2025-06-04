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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StaffSceneController implements Initializable {

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, String> appointmentDateColm;
    @FXML private TableColumn<Appointment, Integer> appointmentIdColm;
    @FXML private TableColumn<Appointment, String> doctorNameColm;
    @FXML private TableColumn<Appointment, Integer> patientIdColm;
    @FXML private TableColumn<Appointment, String> statusColm;
    @FXML private ComboBox<String> selectStatus;
    @FXML private Label emailLabel;
    @FXML private Label passwordLabel;
    @FXML private Label roleSelectionLabel;
    @FXML private DatePicker selectAppointmentDate;
    @FXML private TextField txtDoctorName;
    @FXML private TextField txtPatientId;
 
    private Stage stage;
   	private Scene scene;
   	private Parent root;
   	String[] appointmentStatus = {"Done", "Confirmed", "Pending", "Cancelled"};
    
   	// Log out
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
    
    public void switchToViewPatients(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("staffDashboard2.fxml"));
	    root = loader.load();

	    StaffSceneController2 controller = loader.getController();
	    try {
			controller.loadPatients(null);
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
    
 	public void insertAppointment(ActionEvent event) throws SQLException {
 		String patient_id = txtPatientId.getText().trim();
 		String doctor_name = txtDoctorName.getText().trim();
 		LocalDate appt_date = selectAppointmentDate.getValue();
 		String appt_status = selectStatus.getValue();
 		 
 		// Checks if appointment fields are empty
 		if (!txtPatientId.getText().isEmpty() && 
 			!(appt_date == null) && 
 			!(appt_status == null)) {
 				try (Connection conn = MedAssistantDB.getConnection()){
 					Statement stmt = conn.createStatement();
 					String sql = "INSERT INTO Appointments"
 							+ "(patient_id, doctor_name, appointment_date, status)"
 							+ "VALUES('"
 							+ patient_id + "', '"
 							+ doctor_name + "', '"
 							+ java.sql.Date.valueOf(appt_date) + "', '"
 							+ appt_status + "')";
 					stmt.executeUpdate(sql);
// 					Appointment newAppointment= new Appointment(null, patient_id, doctor_name, appt_date, appt_status);
// 					appointmentTable.getItems().add(newAppointment); - for front end temporary displayed.
 					loadAppointments(null); // to update the table view automatically.
 				} catch(SQLException e) {
 					e.printStackTrace();
 				}

 				txtPatientId.clear();
 				txtDoctorName.clear();
 				selectAppointmentDate.setValue(null);
 				selectStatus.setValue(null);
 		} else {
 			new Alert(Alert.AlertType.INFORMATION, "Please fill all the fields.").show();
 		}
 	}
 	
 	public void loadAppointments(ActionEvent event) throws SQLException {
 		ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
 		
 		try(Connection conn = MedAssistantDB.getConnection()) {
 			Statement stmt = conn.createStatement();
 			String sql = "SELECT * FROM Appointments;";
 			ResultSet rs = stmt.executeQuery(sql);
 			
 			while(rs.next()) {
 				Appointment newAppointment = new Appointment(
 						rs.getInt("appointment_id"), 
 						rs.getInt("patient_id"), 
 						rs.getString("doctor_name"), 
 						rs.getDate("appointment_date").toLocalDate(), 
 						rs.getString("status")
 						);
 					appointmentList.add(newAppointment); 
 				}
 				// Adds the appointmentList to the table.
 				appointmentsTable.setItems(appointmentList);
 			} catch(Exception e) {
 			new Alert(Alert.AlertType.ERROR, "Error Occured");
 		}
 	}
 	
 	// Method to update an appointment
 	public void updateAppointment(ActionEvent event) throws SQLException {
 		Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
 		
 		if (selectedAppointment == null) {	
 			new Alert(Alert.AlertType.ERROR, "Please select a row to update").show();
 			return;
 		}
 		
 		try (Connection conn = MedAssistantDB.getConnection()) {
 			Statement stmt = conn.createStatement();
 			String sql = "UPDATE Appointments "
 					+ "SET patient_id = '" + txtPatientId.getText() + "', "
 					+ "doctor_name = '" + txtDoctorName.getText() + "', "
 					+ "appointment_date = '" + java.sql.Date.valueOf(selectAppointmentDate.getValue()) + "', "
 					+ "status = '" + selectStatus.getValue() + "'"
 					+ "WHERE appointment_id = " + selectedAppointment.getId() + ";";	
 			
 			stmt.executeUpdate(sql); // executes the query.
 			loadAppointments(null); // refreshes the table.
 		} catch(SQLException e) {
 			new Alert(Alert.AlertType.ERROR, "Database Error").show();
 		} catch(NullPointerException e) {
 			new Alert(Alert.AlertType.ERROR, "Please select a row to update").show();
 		}
 		
 		 txtDoctorName.clear();
 		 txtPatientId.clear();
 		 selectAppointmentDate.setValue(null);
 		 selectStatus.setValue(null);
 	}
 	
 	// Puts a row to the text fields.
 	public void editAppointment(ActionEvent event) {
 		Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
 		
 		if (selectedAppointment == null) {	
 			new Alert(Alert.AlertType.ERROR, "Please select a row to update").show();
 			return;
 		}
 		
 		txtPatientId.setText(String.valueOf(selectedAppointment.getPatientID()));
 		txtDoctorName.setText(selectedAppointment.getDoctorName());
 		selectAppointmentDate.setValue(selectedAppointment.getAppointmentDate());
 		selectStatus.setValue(selectedAppointment.getStatus());
 	}
 	
 	// Method to delete an appointment
 	public void deleteAppointment(ActionEvent event) throws SQLException {
 		// Variable that gets the selected row from the table view.
 		Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();

 		try (Connection conn = MedAssistantDB.getConnection()) {
 		    if (selectedAppointment != null) {
 		    	Integer appointment_id = selectedAppointment.getId();
 		    	Statement stmt = conn.createStatement();
 		    	
 		    	String sql = "DELETE FROM Appointments WHERE appointment_id = " + appointment_id;
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
		appointmentIdColm.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
		patientIdColm.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("patientID"));
		doctorNameColm.setCellValueFactory(new PropertyValueFactory<Appointment, String>("doctorName"));
		appointmentDateColm.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentDate"));
		statusColm.setCellValueFactory(new PropertyValueFactory<Appointment, String>("status"));
		
		try {
			loadAppointments(null); // initializes the data from the database to table view.
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		selectStatus.getItems().addAll(appointmentStatus);
	}

}

