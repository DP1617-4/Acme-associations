
package controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.ManagerService;
import domain.CreditCard;
import domain.Manager;
import forms.RegisterManager;

@Controller
@RequestMapping("/_manager")
public class ManagerController {

	//Services

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private CreditCardService	creditCardService;


	//Constructor

	public ManagerController() {
		super();
	}

	//Register

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result;
		RegisterManager registerManager;

		registerManager = new RegisterManager();
		result = this.createEditModelAndView(registerManager);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final RegisterManager registerManager, final BindingResult binding) {
		ModelAndView result;
		ArrayList<Object> aux;
		Manager manager;
		CreditCard creditCard;

		aux = this.managerService.reconstruct(registerManager, binding);
		manager = (Manager) aux.get(0);
		creditCard = (CreditCard) aux.get(1);
		if (binding.hasErrors())
			result = this.createEditModelAndView(registerManager);
		else
			try {
				manager = this.managerService.save(manager);
				creditCard.setHolder(manager);
				creditCard = this.creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				registerManager.setAccept(false);
				result = this.createEditModelAndView(registerManager, "manager.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = true) final int managerId) {
		ModelAndView result;

		Manager manager;

		manager = this.managerService.findOne(managerId);

		result = new ModelAndView("manager/display");
		result.addObject("manager", manager);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final RegisterManager registerManager) {
		ModelAndView result;

		result = this.createEditModelAndView(registerManager, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final RegisterManager registerManager, final String message) {
		ModelAndView result;

		final String requestURI = "_manager/edit.do";

		result = new ModelAndView("manager/register");
		result.addObject("registerManager", registerManager);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
