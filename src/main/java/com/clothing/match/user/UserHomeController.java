package com.clothing.match.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserHomeController {
	
	@RequestMapping(value={"" , "/"} , 
	         method = RequestMethod.GET)
	   public String home() {
	         System.out.println("[UserHomeController] home()");
	         
	         String nextPage = "user/home";
	         
	         return nextPage;
	         
	      }
}
