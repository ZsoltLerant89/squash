package pti.sb_squash_rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import pti.sb_squash_rest.service.AppService;

@RestController
public class AppController {

	private AppService service;
	
	@Autowired
	public AppController(AppService service)
	{
		super();
		this.service = service;
	}
	
//	@GetMapping("/geteuro")
//	public 
}
