package pti.sb_squash_mvc.dto;

import java.util.ArrayList;
import java.util.List;

public class GameDTOList {

	private List<GameDTO> gameDTOList;
	private int userID;
	private List<GameDTO> searchedUser;
	private List<UserDTO> userList;
	
	
	public GameDTOList(int userID) {
		super();
		this.gameDTOList = new ArrayList<>();
		this.userID = userID;
		this.searchedUser = new ArrayList<>();
		this.userList = new ArrayList<>();
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


	public List<GameDTO> getSearchedUser() {
		return searchedUser;
	}


	public void setSearchedUser(List<GameDTO> searchedUser) {
		this.searchedUser = searchedUser;
	}
	
	public void addToSearchedUser(GameDTO gameDTO)
	{
		this.searchedUser.add(gameDTO);
	}

	
	
	public List<UserDTO> getUserList() {
		return userList;
	}


	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}


	public void addUserDTOListTouserDTOList(List<UserDTO> userDTOList)
	{
		this.userList = userDTOList;
	}


	@Override
	public String toString() {
		return "GameDTOList [gameDTOList=" + gameDTOList + ", userID=" + userID + ", searchedUser=" + searchedUser
				+ ", userList=" + userList + "]";
	}
	
	
	
	
	
	
	
}
