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

import services.UserService;
import controllers.AbstractController;
import domain.User;

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

		user = this.userService.findByPrincipal();
		result = this.createEditModelAndView(user);

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid User user, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {

			result = this.createEditModelAndView(user);
		} else {
			try {
				this.userService.phoneValidator(user.getPhoneNumber());
			} catch (final Throwable oops) {
				binding.rejectValue("phoneNumber", "error.object", "The phone number is not valid");
			}

			final User userR = this.userService.reconstructPrincipal(user, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(user);
			else
				try {
					user = this.userService.save(userR);
					result = new ModelAndView("redirect:/actor/actor/displayOwn.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(user, "user.commit.error");
				}
		}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final User user) {
		ModelAndView result;

		result = this.createEditModelAndView(user, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final User user, final String message) {
		ModelAndView result;

		final String requestURI = "user/user/edit.do";

		result = new ModelAndView("user/edit");
		result.addObject("user", user);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
