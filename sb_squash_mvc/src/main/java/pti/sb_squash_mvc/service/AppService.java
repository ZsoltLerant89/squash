package pti.sb_squash_mvc.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pti.sb_squash_mvc.db.Database;
import pti.sb_squash_mvc.dto.AdminDTO;
import pti.sb_squash_mvc.dto.GameDTO;
import pti.sb_squash_mvc.dto.GameDTOList;
import pti.sb_squash_mvc.dto.LocationDTO;
import pti.sb_squash_mvc.dto.UserDTO;
import pti.sb_squash_mvc.model.Currency;
import pti.sb_squash_mvc.model.Game;
import pti.sb_squash_mvc.model.Location;
import pti.sb_squash_mvc.model.RolesOfUsers;
import pti.sb_squash_mvc.model.User;
import pti.sb_squash_mvc.parser.XMLParser;

@Service
public class AppService {
	
	private Database db;
	private XMLParser parser;
	
	@Autowired
	public AppService(Database db, XMLParser parser)
	{
		this.db = db;
		this.parser = parser;
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
		
		if (user != null)
		{
			user.setPassword(password);
			user.setValidPassword(true);
			user.setLoggedin(true);
			db.updateUser(user);
		}
	
		return gameDTOList;
	}

	private UserDTO convertUserToUserDTO(User user)
	{
		UserDTO userDTO = new UserDTO(user.getUserID(),
									  user.getUsername(),
									  user.isValidPassword(),
									  user.getRole()
									  );

		return userDTO;
	}
	
	public GameDTOList getGameDTOList(int userID, String userName, String locationName) {
		
		User user = db.getUserByID(userID);
		GameDTOList gameDTOList = null ;
		
		if (checkUserAndLogin(user))
		{
		
			gameDTOList = new GameDTOList(userID);
			
			List<Game> gameList = db.getGames();
			
			/** Currency */
			
			RestTemplate rt = new RestTemplate();
			Currency currency = rt.getForObject("http://localhost:8081/geteuro", Currency.class);
			
			
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
				
				double rentFeePerHourInEur = (location.getRentFeePerHour() / currency.getValue());
				
				LocationDTO locationDTO = new LocationDTO(	locationID,
															location.getLocationName(),
															location.getLocationAddress(),
															location.getRentFeePerHour(),
															rentFeePerHourInEur
															);
				
				if(	(firstUserDTO.getUserName().equals(userName)) || 
					(secondUserDTO.getUserName().equals(userName))
					)
				{	
					GameDTO currentGameDTO = new GameDTO(currentGame.getGameID(),
														 firstUserDTO,
														 currentGame.getFirstUserScore(),
														 secondUserDTO,
														 currentGame.getSecondUserScore(),
														 locationDTO,
														 currentGame.getDate()
														);
					
					gameDTOList.addTogameDTOList(currentGameDTO);
				}
				
				if(locationDTO.getLocationName().equals(locationName))
				{	
					GameDTO currentGameDTO = new GameDTO(currentGame.getGameID(),
														 firstUserDTO,
														 currentGame.getFirstUserScore(),
														 secondUserDTO,
														 currentGame.getSecondUserScore(),
														 locationDTO,
														 currentGame.getDate()
														 );
				
					gameDTOList.addTogameDTOList(currentGameDTO);
				}
				
				if((userName == null) && locationName == null) 
				{
					GameDTO currentGameDTO = new GameDTO(currentGame.getGameID(),
							 firstUserDTO,
							 currentGame.getFirstUserScore(),
							 secondUserDTO,
							 currentGame.getSecondUserScore(),
							 locationDTO,
							 currentGame.getDate()
							);
					
					gameDTOList.addTogameDTOList(currentGameDTO);
				}
				
			}

			List<UserDTO> userDTOList = getUserDTOList();

			List<LocationDTO>locationDTOList = getLocationDTOList();
			
			gameDTOList.setUserList(userDTOList);
			gameDTOList.setLocationList(locationDTOList);
			
		}
		
		gameDTOList.orderByDate();
		return gameDTOList;
	}


	public AdminDTO regUser(int userID,
							String userName, 
							RolesOfUsers role
							) 
	{
		AdminDTO adminDTO = null;
		
		User user = db.getUserByID(userID);
		
		if(checkAdminAndLogin(user))
		{	
			
			String password = generateRandomChars ();
			
			User newUser = new User(userName,password,role);
			db.persistUser(newUser);
			adminDTO =  getAdminDTO(userID);
		}	
		

		return adminDTO;
	}

	public AdminDTO regLocation(int userID,
								String locationName,
								String locationAddress,
								int rentFeePerHour
								) 
	{
		AdminDTO adminDTO = null;
		User user = db.getUserByID(userID);
		
		if(checkAdminAndLogin(user))
		{
	
			Location newLocation = new Location(locationName,
											 locationAddress,
											 rentFeePerHour
											 );
			
			db.persistLocation(newLocation);
			adminDTO =  getAdminDTO(userID);
		}
		
		return adminDTO;
	}

	public AdminDTO regGame(int userID, 
							int firstUserID, 
							int secondUserID, 
							int gameLocationID, 
							int firstUserScore,
							int secondUserScore,
							LocalDate date
							) 
	{
		AdminDTO adminDTO = null;
		
		User user = db.getUserByID(userID);
		
		if(checkAdminAndLogin(user))
		{
			adminDTO =  getAdminDTO(userID);
			
			Game game = new Game(
								 firstUserID,
								 secondUserID, 
								 gameLocationID, 
								 firstUserScore, 
								 secondUserScore,
								 date
								 );
			
			db.persistGame(game);
		
		}
		
		return adminDTO;
	}

	public AdminDTO getAdminDTO(int userID) 
	{
		AdminDTO adminDTO = null;
		
		User user = db.getUserByID(userID);
		
		if (checkAdminAndLogin(user))
		{
			UserDTO adminUserDTO = new UserDTO(	userID,
												user.getUsername(),
												user.isValidPassword(),
												user.getRole()
												);
	
			List<UserDTO> userDTOList = getUserDTOList();	
			List<LocationDTO> locationDTOList = getLocationDTOList();
			
			adminDTO = new AdminDTO(adminUserDTO);
			
			adminDTO.setUserDTOList(userDTOList);
			adminDTO.setLocationDTOList(locationDTOList);
			
		}
		return adminDTO;
	}
	
	
   private String generateRandomChars () {
	   StringBuilder sb = new StringBuilder ();
	   Random random = new Random ();
	  
	   String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	   int length = 5;

	   for (int i = 0; i < length; i ++) {
		   sb.append (candidateChars.charAt (random.nextInt (candidateChars
                .length ())));
	   }

	   return sb.toString ();
    }

	public UserDTO logOut(int userID) {
		UserDTO userDTO = null;
		
		User user = db.getUserByID(userID);
		
		if (checkUserAndLogin(user) == true )
		{
			user.setLoggedin(false);
			
			db.updateUser(user);
			
			userDTO = convertUserToUserDTO(user);
		}

		return userDTO;
	}

	private boolean checkUserAndLogin(User user)
	{
		boolean result = false;
		
		if ((user != null) && user.isLoggedin())
		{
			result = true;
		}
		
		return result;
	}
	
	private boolean checkAdminAndLogin(User user)
	{
		boolean result = false;
		
		if((user != null) && (user.isLoggedin() == true) && (user.getRole() == RolesOfUsers.ADMIN))
		{
			result = true;
		}
		
		return result;
	}

	public void exportGameDTOsToXML(int userID) throws IOException {	
		
		GameDTOList gameDTOList = getGameDTOList(userID, null,null);
		List<GameDTO> gameDTOs = gameDTOList.getGameDTOList();
		
		parser.exportGameDTOsToXML(gameDTOs);
		
	}

	public GameDTOList importGameDTOsFromXML(int userID, String path ) throws JDOMException, IOException {
		
		GameDTOList gameDTOList = null;
		
		User user = db.getUserByID(userID);
		
		if(checkAdminAndLogin(user))
		{
			List<GameDTO> gameDTOListFromXML = parser.getGameDTOListFromXML(path);
			
			gameDTOList = new GameDTOList(userID);
			
			for(int index = 0; index < gameDTOListFromXML.size(); index++)
			{
				GameDTO currentGameDTO = gameDTOListFromXML.get(index);
				gameDTOList.addTogameDTOList(currentGameDTO);
			}
		}
		
		return gameDTOList;
	}
	
	private List<UserDTO> getUserDTOList()
	{
		List<User>	userList = db.getUsers();
		List<UserDTO> userDTOList = new ArrayList<>();
		
		for(int userIndex = 0; userIndex < userList.size(); userIndex++)
		{
			User currentUser = userList.get(userIndex);
			UserDTO currentUserDTO = new UserDTO(currentUser.getUserID(),
												 currentUser.getUsername(),
												 currentUser.isValidPassword(),
												 currentUser.getRole()
												 );
			
			userDTOList.add(currentUserDTO);	
		}
		
		return userDTOList;
	}

	private List<LocationDTO> getLocationDTOList()
	{
		List<Location> locationList = db.getLocations();
		List<LocationDTO> locationDTOList = new ArrayList<>();
		
		/** Currency */
		
		RestTemplate rt = new RestTemplate();
		Currency currency = rt.getForObject("http://localhost:8081/geteuro", Currency.class);
		
		for(int locationIndex = 0; locationIndex < locationList.size(); locationIndex++)
		{
			
			Location currentLocation = locationList.get(locationIndex);
			
			double rentFeePerHourInEur = (currentLocation.getRentFeePerHour() / currency.getValue());
			
			LocationDTO currentLocationDTO = new LocationDTO(currentLocation.getLocationID(),
															 currentLocation.getLocationName(),
															 currentLocation.getLocationAddress(),
															 currentLocation.getRentFeePerHour(),
															 rentFeePerHourInEur
															 );
			
			locationDTOList.add(currentLocationDTO);	
		}
		
		return locationDTOList;
		
	}
}
