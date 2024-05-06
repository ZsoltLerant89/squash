package pti.sb_squash_mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pti.sb_squash_mvc.db.Database;
import pti.sb_squash_mvc.dto.AdminDTO;
import pti.sb_squash_mvc.dto.GameDTO;
import pti.sb_squash_mvc.dto.GameDTOList;
import pti.sb_squash_mvc.dto.LocationDTO;
import pti.sb_squash_mvc.dto.UserDTO;
import pti.sb_squash_mvc.model.Game;
import pti.sb_squash_mvc.model.Location;
import pti.sb_squash_mvc.model.RolesOfUsers;
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
		
		User user = db.getUserByNameAndPassword(userName, password);
	
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
	
	public GameDTOList updatePasswordAndLogin(int userID, String password) 
	{
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
		UserDTO userDTO = new UserDTO(user.getUserID(),
									  user.getUsername(),
									  user.isValidPassword(),
									  user.getRole()
									  );

		return userDTO;
	}
	
	public GameDTOList getGameDTOList(int userID, String userName) {
		
		User user = db.getUserByID(userID);
		GameDTOList gameDTOList = null ;
		
		if ((user != null) && user.isLoggedin() == true )
		{
		
			gameDTOList = new GameDTOList(userID);
			
			List<Game> gameList = db.getGames();
			
			List<User> userList = null;
			List<UserDTO> userDTOList = null;
			
			List<Location> locationList = null;
			List<LocationDTO> locationDTOList = null;
			
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
				
				LocationDTO locationDTO = new LocationDTO(	locationID,
															location.getLocationName(),
															location.getLocationAddress(),
															location.getRentFeePerHour()
															);
				
				if(	(firstUserDTO.getUserName().equals(userName)) || 
					(secondUserDTO.getUserName().equals(userName)) || 
					(userName == null)
					)
				{	
					GameDTO currentGameDTO = new GameDTO(currentGame.getGameID(),
														 firstUserDTO,
														 currentGame.getFirstUserScore(),
														 secondUserDTO,
														 currentGame.getSecondUserScore(),
														 locationDTO	
														);
					
					gameDTOList.addTogameDTOList(currentGameDTO);
				}
				else if((locationDTO.getLocationName().equals(userName)) || 
						(userName == null)
						)
				{	
					GameDTO currentGameDTO = new GameDTO(currentGame.getGameID(),
														 firstUserDTO,
														 currentGame.getFirstUserScore(),
														 secondUserDTO,
														 currentGame.getSecondUserScore(),
														 locationDTO	
														);
					gameDTOList.addTogameDTOList(currentGameDTO);
				}
				
				
				userList = new ArrayList<>();
				userDTOList = new ArrayList<>();
				
				userList = db.getUsers();
				
				locationList = new ArrayList<>();
				locationDTOList = new ArrayList<>();
				
				locationList = db.getLocations();
				
				for(int userIndex = 0; userIndex < userList.size(); userIndex++)
				{
					User currentUser = userList.get(userIndex);
					UserDTO currentUserDTO = new UserDTO(currentUser.getUserID(),
														 currentUser.getUsername(),
														 currentUser.isValidPassword(),
														 currentUser.getRole());
					
					userDTOList.add(currentUserDTO);	
				}
				
				for(int locationIndex = 0; locationIndex < locationList.size(); locationIndex++)
				{
					
					Location currentLocation = locationList.get(locationIndex);
					LocationDTO currentLocationDTO = new LocationDTO(currentLocation.getLocationID(),
																	 currentLocation.getLocationName(),
																	 currentLocation.getLocationAddress(),
																	 currentLocation.getRentFeePerHour()
																	 );
					
					locationDTOList.add(currentLocationDTO);	
				}
			
				gameDTOList.setUserList(userDTOList);
				gameDTOList.setLocationList(locationDTOList);
		
			 }

		}
		
		return gameDTOList;
	}

	public UserDTO getUserByID(int userID) 
	{
		UserDTO userDTO = null;
		
		User user = db.getUserByID(userID);
		
		userDTO = new UserDTO(	user.getUserID(),
								user.getUsername(),
								user.isValidPassword(),
								user.getRole()
								);
		
		return userDTO;
	}

	public AdminDTO regUser(int userID,
							String userName, 
							String password, 
							RolesOfUsers role
							) 
	{
		AdminDTO adminDTO = null;
		
		User user = new User(userName,password,role);
		db.persistUser(user);
		User admin = db.getUserByID(userID);
		UserDTO adminUserDTO = new UserDTO(	userID,
											admin.getUsername(),
											admin.isValidPassword(),
											admin.getRole()
											);
		
		adminDTO = getAdminDTO(userID);
		adminDTO.setAdmin(adminUserDTO);
		
		
		return adminDTO;
	}

	public AdminDTO regLocation(int userID,
								String locationName,
								String locationAddress,
								int rentFeePerHour
								) 
	{
		AdminDTO adminDTO = null;
		
		Location location = new Location(locationName,
										 locationAddress,
										 rentFeePerHour
										 );
		db.persistLocation(location);
		
		User admin = db.getUserByID(userID);
		
		UserDTO adminUserDTO = new UserDTO(	userID,
											admin.getUsername(), 
											admin.isValidPassword(), 
											admin.getRole()
											);
		
		adminDTO = getAdminDTO(userID);
		adminDTO.setAdmin(adminUserDTO);
	
		
		return adminDTO;
	}

	public AdminDTO regGame(int userID, 
							int firstUserID, 
							int secondUserID, 
							int gameLocationID, 
							int firstUserScore,
							int secondUserScore
							) 
	{
		AdminDTO adminDTO = null;
		
		Game game = new Game(firstUserID,
							 secondUserID, 
							 gameLocationID, 
							 firstUserScore, 
							 secondUserScore
							 );
		
		db.persistGame(game);
		
		User admin = db.getUserByID(userID);
		UserDTO adminUserDTO = new UserDTO( userID,
											admin.getUsername(),
											admin.isValidPassword(),
											admin.getRole()
											);
		
		GameDTOList gameDTOList = getGameDTOList(userID,null);
		List<UserDTO> userlist = gameDTOList.getUserList();
		
		
		adminDTO = getAdminDTO(userID);
		adminDTO.setAdmin(adminUserDTO);
		adminDTO.setUserDTOList(userlist);
		
		return adminDTO;
	}

	public AdminDTO getAdminDTO(int userID) 
	{
		AdminDTO adminDTO = null;
		
		User admin = db.getUserByID(userID);
		UserDTO adminUserDTO = new UserDTO(	userID,
											admin.getUsername(),
											admin.isValidPassword(),
											admin.getRole()
											);
		
		GameDTOList gameDTOList = getGameDTOList(userID,null);
		
		List<UserDTO> userList = gameDTOList.getUserList();	
		List<LocationDTO> locationList = gameDTOList.getLocationList();
		
		adminDTO = new AdminDTO(adminUserDTO);
		
		adminDTO.setUserDTOList(userList);
		adminDTO.setLocationDTOList(locationList);
		
		return adminDTO;
	}
	
}
