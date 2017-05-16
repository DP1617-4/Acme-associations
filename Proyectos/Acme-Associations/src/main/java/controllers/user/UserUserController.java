/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.User;
import services.UserService;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public UserUserController() {
		super();
	}


	@Autowired
	private UserService	userService;


	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		User user;

		user = userService.findByPrincipal();
		result = createEditModelAndView(user);

		return result;
		
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid User user, BindingResult binding) {
		ModelAndView result;
		
		try{
			userService.phoneValidator(user);
		}catch (Throwable oops){
			binding.rejectValue("phoneNumber","error.object","The phone number is not valid");
		}
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(user);
		} else {
			try {
				user = userService.save(user);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(user, "user.commit.error");
			}
		}
		return result;
	}
	
	// Ancillary methods
	
			protected ModelAndView createEditModelAndView(User user) {
				ModelAndView result;

				result = createEditModelAndView(user, null);

				return result;
			}
			protected ModelAndView createEditModelAndView(User user, String message) {
				ModelAndView result;

				String requestURI = "user/user/edit.do";

				result = new ModelAndView("user/edit");
				result.addObject("user", user);
				result.addObject("message", message);
				result.addObject("requestURI", requestURI);

				return result;
			}
}
