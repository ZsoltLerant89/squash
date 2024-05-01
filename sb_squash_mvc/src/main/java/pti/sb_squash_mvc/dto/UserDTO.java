package pti.sb_squash_mvc.dto;

public class UserDTO {

	private int userID;
	private String userName;
	private boolean validPassword;
	
	
	public UserDTO(	int userID,
					String userName, 
					boolean validPassword
					) 
	{
		super();
		this.userID = userID;
		this.userName = userName;
		this.validPassword = validPassword;
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


	@Override
	public String toString() {
		return "UserDTO [userID=" + userID + ", userName=" + userName + ", validPassword=" + validPassword + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
