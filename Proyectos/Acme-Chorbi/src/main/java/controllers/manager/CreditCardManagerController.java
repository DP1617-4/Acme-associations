
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import domain.CreditCard;

@Controller
@RequestMapping("/creditCard/_manager")
public class CreditCardManagerController {

	//Services

	@Autowired
	private CreditCardService	creditCardService;


	//Constructor

	public CreditCardManagerController() {
		super();
	}

	//edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		CreditCard creditCard;

		creditCard = this.creditCardService.findByPrincipal();
		if (creditCard == null)
			creditCard = this.creditCardService.create();
		else
			creditCard.setCreditCardNumber(this.creditCardService.trimCreditNumber(creditCard));
		result = this.createEditModelAndView(creditCard);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CreditCard creditCard, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(creditCard);
		else
			try {
				creditCard = this.creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/creditCard/_manager/edit.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(creditCard, "manager.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete() {
		ModelAndView result;
		CreditCard creditCard;

		creditCard = this.creditCardService.findByPrincipal();
		this.creditCardService.delete(creditCard);

		result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final CreditCard creditCard) {
		ModelAndView result;

		result = this.createEditModelAndView(creditCard, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final String message) {
		ModelAndView result;

		final String requestURI = "creditCard/_manager/edit.do";

		result = new ModelAndView("creditCard/edit");
		result.addObject("creditCard", creditCard);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
