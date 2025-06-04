package application;

import java.time.LocalDate;

public class User {
	private Integer userId;
	private String username;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	private String userPassword;
	private String userContact;
	private LocalDate userBirthdate;
	private String role;
	
	public Integer getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getUserFirstName() {
		return userFirstName;
	}
	
	public String getUserLastName() {
		return userLastName;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public String getUserContact() {
		return userContact;
	}
	
	public LocalDate getUserBirthdate() {
		return userBirthdate;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}
	
	public void setUserBirthdate(LocalDate userBirthdate) {
		this.userBirthdate = userBirthdate;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public User(Integer userId, String username, String userFirstName, String userLastName, String userEmail,
			String userPassword, LocalDate date, String userContact, String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userContact = userContact;
		this.userBirthdate = date;
		this.role = role;
	}
	
	
}
