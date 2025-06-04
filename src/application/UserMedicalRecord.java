package application;

public class UserMedicalRecord {
	private String doctorName;
	private String prescription;
	private String diagnosis;
	
	public String getDoctorName() {
		return doctorName;
	}
	
	public String getPrescription() {
		return prescription;
	}
	
	public String getDiagnosis() {
		return diagnosis;
	}
	
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public UserMedicalRecord(String doctorName, String diagnosis, String prescription) {
		super();
		this.doctorName = doctorName;
		this.diagnosis = diagnosis;
		this.prescription = prescription;
	}
}
