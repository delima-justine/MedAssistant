package application;

public class User {
	private Integer userId;
	private String username;
	private String role;
	
	public Integer getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

	public void setUserId(Integer id) {
		this.userId = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User(Integer id, String username, String role) {
		super();
		this.userId = id;
		this.username = username;
		this.role = role;
	}
	
	
}
