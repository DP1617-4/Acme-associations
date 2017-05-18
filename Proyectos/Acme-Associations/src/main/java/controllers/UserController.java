/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.User;
import services.UserService;
import forms.RegisterUser;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}


	@Autowired
	private UserService	userService;


	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		RegisterUser registerUser;

		registerUser = new RegisterUser();
		result = createEditModelAndView(registerUser);

		return result;
		
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid RegisterUser registerUser, BindingResult binding) {
		ModelAndView result;
		User user;
		user = userService.reconstruct(registerUser, binding);
		try{
			userService.phoneValidator(user);
		}catch (Throwable oops){
			binding.rejectValue("phoneNumber","error.object","The phone number is not valid");
		}
		
		if (binding.hasErrors()) {
			registerUser.setAccept(false);
			result = createEditModelAndView(registerUser);
		} else {
			try {
				user = userService.register(user);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				registerUser.setAccept(false);
				result = createEditModelAndView(registerUser, "user.commit.error");
			}
		}
		return result;
	}
	
	// Ancillary methods
	
			protected ModelAndView createEditModelAndView(RegisterUser registerUser) {
				ModelAndView result;

				result = createEditModelAndView(registerUser, null);

				return result;
			}
			protected ModelAndView createEditModelAndView(RegisterUser registerUser, String message) {
				ModelAndView result;

				String requestURI = "user/edit.do";

				result = new ModelAndView("user/register");
				result.addObject("registerUser", registerUser);
				result.addObject("message", message);
				result.addObject("requestURI", requestURI);

				return result;
			}
}
