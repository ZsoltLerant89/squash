package pti.sb_squash_mvc.dto;

public class UserDTO {

	private int userID;
	private String userName;
	
	
	public UserDTO(int userID, String userName) {
		super();
		this.userID = userID;
		this.userName = userName;
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
	
	
	
	
	
	
	
	
	
	
	
	
}