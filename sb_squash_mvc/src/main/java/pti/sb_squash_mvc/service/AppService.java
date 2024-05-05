package pti.sb_squash_mvc.service;

import java.util.ArrayList;
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
		UserDTO userDTO = new UserDTO(user.getUserID(),user.getUsername(),user.isValidPassword(),user.getRole());

		return userDTO;
	}
	
	public GameDTOList getGameDTOList(int userID) {
		
		GameDTOList gameDTOList = new GameDTOList(userID);
		
		List<Game> gameList = db.getGames();
		List<UserDTO> userList = new ArrayList<>();
		List<LocationDTO> locationList = new ArrayList<>();
		
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
			
			/** userList */
			boolean isfirstUserInList = false;
			boolean isSecondUserInList = false;
			
			if(userList.size()==0)
			{
				userList.add(firstUserDTO);
				userList.add(secondUserDTO);
			}
			else
			{
				for (int usersIndex = 0; usersIndex < userList.size(); usersIndex++)
				{
					UserDTO currentUserDTO = userList.get(usersIndex);
					String currentUserName = currentUserDTO.getUserName();
					
					if(currentUserName.equals(firstUser.getUsername()))
					{
						isfirstUserInList = true;
						break;	
					}
					else if(currentUserName.equals(secondUser.getUsername()))
					{
						isSecondUserInList = true;
						break;

					}
				
				}
				
			if (isfirstUserInList == false)
			{
				userList.add(firstUserDTO);
			}
			
			if (isSecondUserInList == false)
			{
				userList.add(secondUserDTO);
			}
				
			}
			
			/** locationList */
			boolean isLocationInList = false;
			
			if(locationList.size()==0)
			{
				locationList.add(locationDTO);
			}
			else
			{
				for (int locationIndex = 0; locationIndex < locationList.size(); locationIndex++)
				{
					LocationDTO currentLocationDTO = locationList.get(locationIndex);
					String currentLocationName = currentLocationDTO.getLocationName();
					
					if(currentLocationName.equals(location.getLocationName()))
					{
						isLocationInList = true;
						break;	
					}
				
				}
				
			if (isLocationInList == false)
			{
				locationList.add(locationDTO);
			}
				
			}
		}
		
		gameDTOList.addUserDTOListTouserDTOList(userList);
		
		gameDTOList.addLocationDTOToLocationList(locationList);

		return gameDTOList;
	}

	public GameDTOList getUserByNameFromGameDTOList(int userID,String userName) {
		
		GameDTOList gameDTOList = getGameDTOList(userID);
		
		for(int index = 0; index < gameDTOList.getGameDTOList().size();index++)
		{
			GameDTO currentGameDTO = gameDTOList.getGameDTOList().get(index);
			if((currentGameDTO.getFirstUserDTO().getUserName().equals(userName)) || (currentGameDTO.getSecondUserDTO().getUserName().equals(userName)))
			{
				gameDTOList.addToSearchedUser(currentGameDTO);	
			}
		}
		
		return gameDTOList;
	}

	public GameDTOList getLocationByNameFromGameDTOList(int userID, String locationName) {
		
	GameDTOList gameDTOList = getGameDTOList(userID);
		
		for(int index = 0; index < gameDTOList.getGameDTOList().size();index++)
		{
			GameDTO currentGameDTO = gameDTOList.getGameDTOList().get(index);
			if((currentGameDTO.getLocationDTO().getLocationName().equals(locationName)))
			{
				gameDTOList.addToSearchedLocation(currentGameDTO);
			}
		}
		
		return gameDTOList;
	}

	public UserDTO getUserByID(int userID) {
		
		UserDTO userDTO = null;
		
		User user = db.getUserByID(userID);
		
		userDTO = new UserDTO(user.getUserID(),user.getUsername(),user.isValidPassword(),user.getRole());
		
		return userDTO;
	}


}
