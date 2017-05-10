package controllers.chorbi;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import domain.CreditCard;
import services.CreditCardService;

@Controller
@RequestMapping("/creditCard/chorbi")
public class CreditCardChorbiController {

	//Services

		@Autowired
		private CreditCardService	creditCardService;


		//Constructor

		public CreditCardChorbiController() {
			super();
		}
		
		//edit
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit() {
			ModelAndView result;
			CreditCard creditCard;

			creditCard = creditCardService.findByPrincipal();
			if(creditCard == null){
				creditCard = creditCardService.create();
			}else{
				creditCard.setCreditCardNumber(creditCardService.trimCreditNumber(creditCard));
			}
			result = createEditModelAndView(creditCard);

			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid CreditCard creditCard, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = createEditModelAndView(creditCard);
			} else {
				try {
					creditCard = creditCardService.save(creditCard);
					result = new ModelAndView("redirect:/creditCard/chorbi/edit.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(creditCard, "chorbi.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete() {
			ModelAndView result;
			CreditCard creditCard;

			creditCard = creditCardService.findByPrincipal();
			creditCardService.delete(creditCard);
			
			result = new ModelAndView("redirect:/welcome/index.do");

			return result;
		}
		
		// Ancillary methods
		
		protected ModelAndView createEditModelAndView(CreditCard creditCard) {
			ModelAndView result;

			result = createEditModelAndView(creditCard, null);

			return result;
		}
		protected ModelAndView createEditModelAndView(CreditCard creditCard, String message) {
			ModelAndView result;

			String requestURI = "creditCard/chorbi/edit.do";

			result = new ModelAndView("creditCard/edit");
			result.addObject("creditCard", creditCard);
			result.addObject("message", message);
			result.addObject("requestURI", requestURI);

			return result;
		}
	
}
