package pti.sb_squash_mvc.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash_mvc.dto.AdminDTO;
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
			  	targetPage = "firstlogin.html";
			 
			  }
			  else
			  {
				
			  	GameDTOList gameDTOList = service.getGameDTOList(userDTO.getUserID(),null,null);
			  	
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
		
		GameDTOList gameDTOList = service.getGameDTOList(userID,null,null);
		
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
		
		GameDTOList gameDTOList = service.getGameDTOList(userID,userName,null);
		
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

		GameDTOList gameDTOList = service.getGameDTOList(userID,null,locationName);
		
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
			
		GameDTOList gameDTOList = service.getGameDTOList(userID,null,null);
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
		String targetPage ="";
		
		AdminDTO adminDTO = service.getAdminDTO(userID);
		
		if(adminDTO != null)
		{
			model.addAttribute("adminDTO", adminDTO);
			targetPage = "admin.html";
		}
		else
		{
			GameDTOList gameDTOList = service.getGameDTOList(userID, null,null);
			
			if(gameDTOList != null)
			{

				model.addAttribute("gameDTOList", gameDTOList);
				
				targetPage = "index.html";
			}
			else
			{
				targetPage = "login.html";
			}
		}

		return targetPage;
	}
	
	@PostMapping("/admin/reguser")
	private String regUser(	Model model,
							@RequestParam("userid") int userID,
							@RequestParam("username") String userName,
							@RequestParam ("role") RolesOfUsers role
							)
	{
		
		String targetPage = "login.html";
		
		AdminDTO adminDTO = service.regUser(userID,
											userName,
											role
											);	
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
		String targetPage = "login.html";
		
		AdminDTO adminDTO = service.regLocation(userID,
												locationName,
												locationAddress,
												rentFeePerHour
												);
		if(adminDTO != null)
		{
			model.addAttribute("adminDTO",adminDTO);
			targetPage = "admin.html";
		}
		
		return targetPage;
	}
	
	@PostMapping("/admin/reggame")
	private String regGame(Model model, 
						  @RequestParam("userid") int userID,
						  @RequestParam("firstuserid") int firstUserID,
						  @RequestParam("seconduserid") int secondUserID,
						  @RequestParam("gamelocationid") int gameLocationID,
						  @RequestParam("firstuserscore") int firstUserScore,
						  @RequestParam("seconduserscore") int secondUserScore,
						  @RequestParam("date") LocalDate date
						  )
	{
		String targetPage = "login.html";
		
		
			AdminDTO adminDTO = service.regGame(userID,
												firstUserID,
												secondUserID,
												gameLocationID,
												firstUserScore,
												secondUserScore,
												date
												);
			
			if(adminDTO != null)
			{
				model.addAttribute("adminDTO",adminDTO);
				targetPage = "admin.html";
			}
		
		return targetPage;
	}
	
	@GetMapping("/logout")
	private String logOut(Model model,
						 @RequestParam("userid") int userID
						 )
	
	{
		String targetPage = "index.html";
		
		UserDTO userDTO = service.logOut(userID);
		
		if (userDTO != null)
		{
			model.addAttribute("userDTO", userDTO);
			targetPage = "login.html";
		}
		
		return targetPage;
	}
	
	@PostMapping ("/admin/export")
	public String exportXML(Model model,
						@RequestParam("userid") int userID
						) throws IOException
	{
		
		AdminDTO adminDTO = service.getAdminDTO(userID);
		
		if(adminDTO != null)
		{

		  	model.addAttribute("adminDTO", adminDTO);
		  	
			service.exportGameDTOsToXML(userID);
		}
		
		return "admin.html";
	}
	
	@GetMapping ("/admin/import")
	public String importXML(Model model,
				 @RequestParam("userid") int userID,
				 @RequestParam("path") String path
				 ) throws JDOMException, IOException
	{
		GameDTOList gameDTOList = service.importGameDTOsFromXML(userID,path);
		
		if(gameDTOList != null)
		{
			model.addAttribute("gameDTOList", gameDTOList);
	
		}
		
		return "import.html";
	}
	
}

