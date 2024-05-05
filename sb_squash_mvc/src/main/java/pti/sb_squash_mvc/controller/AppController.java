package pti.sb_squash_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash_mvc.dto.GameDTOList;
import pti.sb_squash_mvc.dto.UserDTO;
import pti.sb_squash_mvc.model.RolesOfUsers;
import pti.sb_squash_mvc.service.AppService;

@Controller
public class AppController {
 
	private AppService service;

	@Autowired
	public AppController(AppService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/log")
	private String loginPage()
	{
		return "login.html";
	}
	
	@PostMapping("/login")
	private String login(Model model,
						@RequestParam("username") String userName,
						@RequestParam("password") String password
						)
	{
		String targetPage = "login.html";
		
		UserDTO userDTO = service.login(userName,password);
			
		if (userDTO != null)
		{
		  if (userDTO.isValidPassword() == false)
		  {

		  	model.addAttribute("userDTO", userDTO);
		  	targetPage = "firstlogin.html";
		 
		  }
		  else
		  {

		  	GameDTOList gameDTOList = service.getGameDTOList(userDTO.getUserID());
		  	model.addAttribute("gameDTOList", gameDTOList);
		  	model.addAttribute("userDTO", userDTO);
		  	targetPage = "index.html";
		  }
		  
		}
		

		return targetPage;
	}

	@PostMapping("/login/firstlogin")
	private String doFirstLogin(Model model,
								@RequestParam("userid") int userID,
								@RequestParam("password") String password
			)
	{
		
		service.updatePasswordAndLogin(userID, password);
		GameDTOList gameDTOList = service.getGameDTOList(userID);
	  	model.addAttribute("gameDTOList", gameDTOList);

		return "index.html";
	}
	
	@GetMapping("/index/searchuser")
	private String searchUserByName(Model model,
									@RequestParam("userid") int userID,
									@RequestParam("username") String userName 
									)
	{
		GameDTOList gameDTOList = service.getGameDTOList(userID);
	  	model.addAttribute("gameDTOList", gameDTOList);
	  	
	  	GameDTOList searchedUserList = service.getUserByNameFromGameDTOList(userID, userName);
	  	model.addAttribute("searchedUserList", searchedUserList);
	  	
	  	
		
		return "index.html";
	}
	
	@GetMapping("/index/searchlocation")
	private String searchLocationByLocationName(Model model,
												@RequestParam("userid") int userID,
												@RequestParam("locationname") String locationName
												)
	{
		
		GameDTOList gameDTOList = service.getGameDTOList(userID);
	  	model.addAttribute("gameDTOList", gameDTOList);
		
		GameDTOList searchedLocationList = service.getLocationByNameFromGameDTOList(userID, locationName);
	  	model.addAttribute("searchedLocationList", searchedLocationList);
		
		return "index.html";
	}
	
	@GetMapping("/index")
	private String returnToIndex(Model model,
								@RequestParam("userid") int userID			
			)
	{
	
		GameDTOList gameDTOList = service.getGameDTOList(userID);
	  	model.addAttribute("gameDTOList", gameDTOList);

		return "index.html";
	}
	
	@GetMapping("/admin")
	private String goToAdminPage(Model model,
								@RequestParam("userid") int userID
								)
	{
		String targetPage ="index.html";
		
		GameDTOList gameDTOList = service.getGameDTOList(userID);
	  	model.addAttribute("gameDTOList", gameDTOList);
		
	  	UserDTO userDTO = service.getUserByID(userID);

	  	RolesOfUsers role = RolesOfUsers.ADMIN;
	  	
		if (userDTO.getRole().equals(role))
		{
			targetPage = "admin.html";
		}
		
		return targetPage;
	}
}
