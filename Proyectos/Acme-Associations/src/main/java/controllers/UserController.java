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

import services.UserService;
import domain.User;
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
		result = this.createEditModelAndView(registerUser);

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegisterUser registerUser, final BindingResult binding) {
		ModelAndView result;
		User user;

		try {
			this.userService.phoneValidator(registerUser.getPhoneNumber());
		} catch (final Throwable oops) {
			binding.rejectValue("phoneNumber", "error.object", "The phone number is not valid");
		}

		if (binding.hasErrors()) {
			registerUser.setAccept(false);
			result = this.createEditModelAndView(registerUser);
		} else
			try {
				user = this.userService.reconstruct(registerUser, binding);
				if (binding.hasErrors())
					result = this.createEditModelAndView(registerUser);
				else {
					user = this.userService.register(user);
					result = new ModelAndView("redirect:/welcome/index.do");
				}
			} catch (final Throwable oops) {
				registerUser.setAccept(false);
				result = this.createEditModelAndView(registerUser, "user.commit.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final RegisterUser registerUser) {
		ModelAndView result;

		result = this.createEditModelAndView(registerUser, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final RegisterUser registerUser, final String message) {
		ModelAndView result;

		final String requestURI = "user/edit.do";

		result = new ModelAndView("user/register");
		result.addObject("registerUser", registerUser);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
