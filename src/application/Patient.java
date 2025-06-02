package application;

import java.time.LocalDate;

public class Patient {
	private String id;
	private String patientID;
	private String doctorName;
	private LocalDate appointmentDate;
	private String status;

	public String getId() {
		return id;
	}

	public String getPatientID() {
		return patientID;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public String getStatus() {
		return status;
	}
	
	public Patient(String id, String patientID, String doctorName, LocalDate appt_date, String status) {
		super();
		this.id = id;
		this.patientID = patientID;
		this.doctorName = doctorName;
		this.appointmentDate = appt_date;
		this.status = status;
	}
}
