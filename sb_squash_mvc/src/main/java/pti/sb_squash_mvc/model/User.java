package pti.sb_squash_mvc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@Column(name = "userid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "validpassword")
	private boolean validPassword;
	
	@Column(name = "role")
	private RolesOfUsers role;
	
	@Column(name = "loggedin")
	private boolean loggedin;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidPassword() {
		return validPassword;
	}

	public void setValidPassword(boolean validPassword) {
		this.validPassword = validPassword;
	}

	public RolesOfUsers getRole() {
		return role;
	}

	public void setRole(RolesOfUsers role) {
		this.role = role;
	}

	public boolean isLoggedin() {
		return loggedin;
	}

	public void setLoggedin(boolean loggedin) {
		this.loggedin = loggedin;
	}

	
	
	
	
}
