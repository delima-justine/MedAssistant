package application;

import java.time.LocalDate;

public class Appointment {
	private Integer id;
	private String patientID;
	private String doctorName;
	private LocalDate appointmentDate;
	private String status;

	public Integer getId() {
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
	
	public Appointment(Integer id, String patientID, String doctorName, LocalDate appt_date, String status) {
		super();
		this.id = id;
		this.patientID = patientID;
		this.doctorName = doctorName;
		this.appointmentDate = appt_date;
		this.status = status;
	}
}
