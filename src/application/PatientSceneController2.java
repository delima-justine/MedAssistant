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

public class PatientSceneController2 implements Initializable {

    @FXML private TableColumn<PatientAppointment, String> appointmentDate;
    @FXML private TableView<PatientAppointment> appointmentTable;
    @FXML private TableColumn<PatientAppointment, String> doctorName;
    @FXML private TableColumn<PatientAppointment, Integer> patientId;
    @FXML private TableColumn<PatientAppointment, String> status;

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
    
    public void switchToViewMedicalRecords(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("patientDashboard3.fxml"));
	    root = loader.load();

	    PatientSceneController3 controller = loader.getController();
	    try {
			controller.loadMedicalRecords(null);
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
    
    public void loadPatientAppointments(ActionEvent event) throws SQLException {
    	ObservableList<PatientAppointment> appointmentsList = FXCollections.observableArrayList();
    	
    	try(Connection conn = MedAssistantDB.getConnection()) {
    		String sql = "SELECT * FROM Appointments WHERE patient_id = " + Session.getPatientId();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
            	PatientAppointment newAppointment = new PatientAppointment(
            			rs.getInt("patient_id"),
            			rs.getString("doctor_name"),
            			rs.getDate("appointment_date").toLocalDate(),
            			rs.getString("status")
            	);
            	appointmentsList.add(newAppointment);
            }
            appointmentTable.setItems(appointmentsList);
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		patientId.setCellValueFactory(new PropertyValueFactory<PatientAppointment, Integer>("patientId"));
		doctorName.setCellValueFactory(new PropertyValueFactory<PatientAppointment, String>("doctorName"));
		appointmentDate.setCellValueFactory(new PropertyValueFactory<PatientAppointment, String>("appointmentDate"));
		status.setCellValueFactory(new PropertyValueFactory<PatientAppointment, String>("status"));
		
		try {
			loadPatientAppointments(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

