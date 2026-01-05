package com.gcu.inventory.controller;

import com.gcu.inventory.model.LoginModel;
import com.gcu.inventory.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

	// Added for milestone 3/ constructor injection
		private final AuthService authService;
		
		// Handles the GET request to root "/"
		@GetMapping("/")
		public String display(Model model) {
			
			// Sets title attribute
			model.addAttribute("title", "Login Form");
			
			// Create MoginModel instance and add it to the model
			model.addAttribute("loginModel", new LoginModel());
			
			// Return view name
			return "login";
		}

    @PostMapping("/doLogin")
    public String doLogin(@Valid LoginModel loginModel,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        // Added for milestone 3
		// Calls the authservice to validate credentials
		// The controller does not perform authentication logic itself
		boolean success = authService.login(loginModel.getUsername(), loginModel.getPassword());
		
		// Added for milestone 3
		// Added for milestone 3
		// If not successful return the login view with an error message
		if (!success)
		{
			model.addAttribute("title", "Login Form");
			model.addAttribute("loginError", "Invalid username or password.");
			//System.out.println("Wrong Username or Password Entered");
			return "login";
		}
		
		System.out.println(String.format("Form with Username of %s and Passowrd of %s", 
				loginModel.getUsername(), loginModel.getPassword()));

        //model.addAttribute("loginError", "Invalid username or password");
        //return "login";
		return "home";
    }
    
    // Added for milestone 3
    /**
	 * Constructor for LoginController
	 * Uses constructor based dependency injection to receive the AuthService
	 * from the Spring IoC container
	 * @param authService
	 */
 	public LoginController(AuthService authService) {
 		this.authService = authService;
 	}
}
