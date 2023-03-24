package com.project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.project.project.model.User;
import com.project.project.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@SessionAttributes("name")
public class UserController {

    @Autowired
    UserRepository service;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model){
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String username, @RequestParam String passhash, HttpServletRequest request){
    	System.out.println(username + passhash);
        User user = service.validateUser(username, passhash);
        if (user != null) {
            request.getSession().setAttribute("userId", user.getId());
            System.out.println(user.toString());
            model.addAttribute("userId", user.getId());
            return "redirect:/products";
        }
        System.out.println("User not found");
        model.put("errorMessage", "Invalid Credentials");
        return "index";
    }

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String showSignUpPage(ModelMap model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute User user, HttpSession session) {
	        // Save user to database
	        userRepository.saveUser(user);
	        // Add user ID to session
	        session.setAttribute("userId", user.getId());
	        return "redirect:/products";
	    }
}
