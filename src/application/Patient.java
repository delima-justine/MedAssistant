package application;

import java.time.LocalDate;

public class Patient {
	private Integer appointmentId;
	private Integer patientId;
	private String patientName;
	private LocalDate appointmentDate;
	private String appointmentStatus;
	
	public Integer getAppointmentId() {
		return appointmentId;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}
	public String getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
	public Patient(Integer appointmentId, Integer patientId, String patientName, LocalDate appointmentDate,
			String appointmentStatus) {
		super();
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.patientName = patientName;
		this.appointmentDate = appointmentDate;
		this.appointmentStatus = appointmentStatus;
	}
}
