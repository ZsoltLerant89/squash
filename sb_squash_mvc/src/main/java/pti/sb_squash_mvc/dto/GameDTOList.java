package pti.sb_squash_mvc.dto;

import java.util.ArrayList;
import java.util.List;

public class GameDTOList {

	private List<GameDTO> gameDTOList;
	private int userID;
	private List<UserDTO> userList;
	private List<LocationDTO> locationList;
	
	
	
	public GameDTOList(int userID) {
		super();
		this.gameDTOList = new ArrayList<>();
		this.userID = userID;
		this.userList = new ArrayList<>();
		this.locationList = new ArrayList<>();
	}


	public List<GameDTO> getGameDTOList() {
		return gameDTOList;
	}

	public void setGameDTOList(List<GameDTO> gameDTOList) {
		this.gameDTOList = gameDTOList;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void addTogameDTOList(GameDTO gameDTO)
	{
		this.gameDTOList.add(gameDTO);
	}

	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}

	public List<LocationDTO> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<LocationDTO> locationList) {
		this.locationList = locationList;
	}


	@Override
	public String toString() {
		return "GameDTOList [gameDTOList=" + gameDTOList + ", userID=" + userID + ", userList=" + userList
				+ ", locationList=" + locationList + "]";
	}


	

	
	
}
