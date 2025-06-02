package application;

import java.time.LocalDate;

public class MedicalRecord {
	private Integer medicalId;
	private String patientId;
	private String diagnosis;
	private String prescription;
	private LocalDate recordDate;
	
	public Integer getMedicalId() {
		return medicalId;
	}

	public String getPatientId() {
		return patientId;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public String getPrescription() {
		return prescription;
	}

	public LocalDate getRecordDate() {
		return recordDate;
	}

	public void setMedicalId(Integer id) {
		this.medicalId = id;
	}

	public void setPatientId(String patientID) {
		this.patientId = patientID;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public void setRecordDate(LocalDate recordDate) {
		this.recordDate = recordDate;
	}

	public MedicalRecord(Integer id, String patientID, String diagnosis, String prescription, LocalDate recordDate) {
		super();
		this.medicalId = id;
		this.patientId = patientID;
		this.diagnosis = diagnosis;
		this.prescription = prescription;
		this.recordDate = recordDate;
	}
}
