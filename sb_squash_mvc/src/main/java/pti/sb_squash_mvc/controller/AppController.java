package pti.sb_squash_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pti.sb_squash_mvc.dto.GameDTOList;
import pti.sb_squash_mvc.dto.UserDTO;
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
	  	
	  	GameDTOList searchList = service.getUserByNameFromGameDTOList(userID, userName);
	  	model.addAttribute("searchList", searchList);
		
		return "index.html";
	}
}
