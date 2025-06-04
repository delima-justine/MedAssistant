package application;

import java.time.LocalDate;

public class PatientAppointment {
	private Integer patientId;
	private String doctorName;
	private LocalDate appointmentDate;
	private String status;
	
	public Integer getPatientId() {
		return patientId;
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
	
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public PatientAppointment(Integer patientId, String doctorName, LocalDate appointmentDate, String status) {
		super();
		this.patientId = patientId;
		this.doctorName = doctorName;
		this.appointmentDate = appointmentDate;
		this.status = status;
	}
}
