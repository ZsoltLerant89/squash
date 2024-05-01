package pti.sb_squash_mvc.dto;

import java.util.ArrayList;
import java.util.List;

public class GameDTOList {

	private List<GameDTO> gameDTOList;
	private int userID;
	
	
	public GameDTOList(int userID) {
		super();
		this.gameDTOList = new ArrayList<>();
		this.userID = userID;
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
	
	
}
