package pti.sb_squash_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash_mvc.dto.AdminDTO;
import pti.sb_squash_mvc.dto.GameDTOList;
import pti.sb_squash_mvc.dto.UserDTO;
import pti.sb_squash_mvc.model.Game;
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
			model.addAttribute("userDTO", userDTO);
			  if (userDTO.isValidPassword() == false)
			  {
			  	targetPage = "firstlogin.html";
			 
			  }
			  else
			  {
				
			  	GameDTOList gameDTOList = service.getGameDTOList(userDTO.getUserID(),null);
			  	
			  	if(gameDTOList != null)
			  	{
				  	model.addAttribute("gameDTOList", gameDTOList);
				  	
				  	targetPage = "index.html";
			  	}
			  	
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
		String targetPage = "login.html";
		service.updatePasswordAndLogin(userID, password);
		
		GameDTOList gameDTOList = service.getGameDTOList(userID,null);
		
		if(gameDTOList != null)
		{
			model.addAttribute("gameDTOList", gameDTOList);
			targetPage = "index.html";
		}

		return targetPage;
	}
	
	@GetMapping("/index/searchuser")
	private String searchUserByName(Model model,
									@RequestParam("userid") int userID,
									@RequestParam("username") String userName 
									)
	{
		String targetPage = "login.html";
		
		GameDTOList gameDTOList = service.getGameDTOList(userID,userName);
		
		if(gameDTOList != null)
		{
			model.addAttribute("gameDTOList", gameDTOList);
			targetPage = "index.html";
		}
		
		return targetPage;
	}
	
	@GetMapping("/index/searchlocation")
	private String searchLocationByLocationName(Model model,
												@RequestParam("userid") int userID,
												@RequestParam("locationname") String locationName
												)
	{
		String targetPage ="login.html";
		
		GameDTOList gameDTOList = service.getGameDTOList(userID,locationName);
		
		if(gameDTOList != null)
		{
			model.addAttribute("gameDTOList", gameDTOList);
			targetPage = "index.html";
		}
		
		return targetPage;
	}
	
	@GetMapping("/index")
	private String returnToIndex(Model model,
								@RequestParam("userid") int userID			
			)
	{
		String targetPage = "login.html";
		
		GameDTOList gameDTOList = service.getGameDTOList(userID,null);
		if(gameDTOList != null)
		{
			model.addAttribute("gameDTOList", gameDTOList);
			targetPage = "index.html";
		}
		return targetPage;
	}
	
	@GetMapping("/admin")
	private String goToAdminPage(Model model,
								@RequestParam("userid") int userID
								)
	{
		String targetPage ="login.html";
		
		GameDTOList gameDTOList = service.getGameDTOList(userID,null);
		if(gameDTOList != null)
		{
		  	model.addAttribute("gameDTOList", gameDTOList);
			
		  	UserDTO userDTO = service.getUserByID(userID);
		  	model.addAttribute("userDTO",userDTO);
		  	
		  	targetPage = "index.html";
			 
		  	RolesOfUsers role = RolesOfUsers.ADMIN;
			if (userDTO.getRole().equals(role))
			{
				AdminDTO adminDTO = service.getAdminDTO(userID);
			  	model.addAttribute("adminDTO", adminDTO);
				targetPage = "admin.html";
			}
		}
		
		return targetPage;
	}
	
	@PostMapping("/admin/reguser")
	private String regUser(	Model model,
							@RequestParam("userid") int userID,
							@RequestParam("username") String userName,
							@RequestParam("password") String password,
							@RequestParam ("role") RolesOfUsers role
							)
	{
		
		String targetPage = "login.html";
		
		AdminDTO adminDTO = service.regUser(userID,userName,password,role);	
		if(adminDTO != null)
		{
			model.addAttribute("adminDTO",adminDTO);
			targetPage = "admin.html";
		}
		return targetPage;
	}
	
	@PostMapping("/admin/reglocation")
	private String regLocation( Model model,
								@RequestParam("userid") int userID,
								@RequestParam("locationname") String locationName,
								@RequestParam("locationaddress") String locationAddress,
								@RequestParam("rentfeeperhour") int rentFeePerHour
								)
	{
		AdminDTO adminDTO = service.regLocation(userID,locationName,locationAddress,rentFeePerHour);
		model.addAttribute("adminDTO",adminDTO);
		
		return "admin.html";
	}
	
	@PostMapping("/admin/reggame")
	private String regGame(Model model, 
						  @RequestParam("userid") int userID,
						  @RequestParam("firstuserid") int firstUserID,
						  @RequestParam("seconduserid") int secondUserID,
						  @RequestParam("gamelocationid") int gameLocationID,
						  @RequestParam("firstuserscore") int firstUserScore,
						  @RequestParam("seconduserscore") int secondUserScore
						  )
	{
		
		AdminDTO adminDTO = service.regGame(userID,firstUserID,secondUserID,gameLocationID,firstUserScore,secondUserScore);
		model.addAttribute("adminDTO",adminDTO);
		
		
		return "admin.html";
	}
}

