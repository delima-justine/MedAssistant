package application;

public class User {
	private Integer userId;
	private String username;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	private String userPassword;
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

	public void setRole(String role) {
		this.role = role;
	}

	public User(Integer userId, String username, String userFirstName, String userLastName, String userEmail,
			String userPassword, String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.role = role;
	}
	
	
	
}
