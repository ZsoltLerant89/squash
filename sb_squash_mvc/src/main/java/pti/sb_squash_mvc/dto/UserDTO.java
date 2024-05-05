package pti.sb_squash_mvc.dto;

import pti.sb_squash_mvc.model.RolesOfUsers;

public class UserDTO {

	private int userID;
	private String userName;
	private boolean validPassword;
	private RolesOfUsers role;
	
	
	public UserDTO(	int userID,
					String userName, 
					boolean validPassword,
					RolesOfUsers role
					) 
	{
		super();
		this.userID = userID;
		this.userName = userName;
		this.validPassword = validPassword;
		this.role = role;
	}


	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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


	@Override
	public String toString() {
		return "UserDTO [userID=" + userID + ", userName=" + userName + ", validPassword=" + validPassword + ", role="
				+ role + "]";
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
