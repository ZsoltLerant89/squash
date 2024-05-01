package pti.sb_squash_mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pti.sb_squash_mvc.db.Database;
import pti.sb_squash_mvc.dto.GameDTO;
import pti.sb_squash_mvc.dto.GameDTOList;
import pti.sb_squash_mvc.dto.LocationDTO;
import pti.sb_squash_mvc.dto.UserDTO;
import pti.sb_squash_mvc.model.Game;
import pti.sb_squash_mvc.model.Location;
import pti.sb_squash_mvc.model.User;

@Service
public class AppService {
	
	private Database db;
	
	@Autowired
	public AppService(Database db)
	{
		this.db = db;
	}

	public UserDTO login(String userName, String password) {
		
		UserDTO userDTO = null;
		
		User user = db.getUserByNameAndPassword(userName,password);
	
		if(user != null)
		{
			if(user.isValidPassword() == true)
			{
				user.setLoggedin(true);
				db.updateUser(user);
			}
			
			userDTO =  convertUserToUserDTO(user);
		}
		
		return userDTO;
	}
	
	public GameDTOList updatePasswordAndLogin(int userID, String password) {
		
		GameDTOList gameDTOList = null;
		
		User user = db.getUserByID(userID);
		
		if(user != null)
		{
			user.setPassword(password);
			user.setValidPassword(true);
			user.setLoggedin(true);
			db.updateUser(user);
		}
		
		
		
		return gameDTOList;
	}

	public UserDTO convertUserToUserDTO(User user)
	{
		UserDTO userDTO = new UserDTO(user.getUserID(),user.getUsername(),user.isValidPassword());

		return userDTO;
	}
	
	public GameDTOList getGameDTOList(int userID) {
		
		GameDTOList gameDTOList = new GameDTOList(userID);
		
		List<Game> gameList = db.getGames();
		
		for(int index = 0; index < gameList.size();index++)
		{
			Game currentGame = gameList.get(index);
			
			int firstUserID = currentGame.getFirstUserID();
			int secondUserID = currentGame.getSecondUserID();
			
			User firstUser = db.getUserByID(firstUserID);
			UserDTO firstUserDTO = convertUserToUserDTO(firstUser);
			
			User secondUser = db.getUserByID(secondUserID);
			UserDTO secondUserDTO = convertUserToUserDTO(secondUser);
			
			int locationID = currentGame.getGameLocationID();
			
			Location location = db.getLocationByID(locationID);
			
			LocationDTO locationDTO = new LocationDTO(locationID,location.getLocationName(),location.getLocationAddress(),location.getRentFeePerHour());
			
			GameDTO currentGameDTO = new GameDTO(currentGame.getGameID(),
												 firstUserDTO,
												 currentGame.getFirstUserScore(),
												 secondUserDTO,
												 currentGame.getSecondUserScore(),
												 locationDTO	
												);
			gameDTOList.addTogameDTOList(currentGameDTO);
			
		}

		return gameDTOList;
	}

	
}
