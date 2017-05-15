
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Association;
import domain.Request;

@Controller
@RequestMapping("/user/request")
public class UserRequestController extends AbstractController {

	public UserRequestController() {
		super();
	}


	@Autowired
	private RequestService	requestService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Request> requests = this.requestService.findAllByPrincipal();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);

		return result;
	}

	@RequestMapping(value = "/{association}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association) {
		ModelAndView result;

		final Collection<Request> requests = this.requestService.findAllByAssociation(association);

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);

		return result;
	}

	@RequestMapping(value = "/{association}/apply", method = RequestMethod.GET)
	public ModelAndView apply(@PathVariable final Association association) {
		ModelAndView result;

		final Request request = this.requestService.create(association);
		result = new ModelAndView("redirect: /welcome/index.do");
		try {
			this.requestService.save(request);
			result.addObject("flashMessage", "request.success");
		} catch (final IllegalArgumentException e) { //Lo suyo ser�a a�adir varios mensajes en el assert del servicio, y se imprime el mensaje (Mensaje con c�digo de int)
			result.addObject("flashMessage", "request.inside");
		}

		return result;
	}

	@RequestMapping(value = "/{request}/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@PathVariable final Request request) {
		ModelAndView result;

		this.requestService.delete(request);

		result = new ModelAndView("redirect: /user/request/list.do");
		result.addObject("flashMessage", "request.deleted");

		return result;
	}

	@RequestMapping(value = "/{request}/accept", method = RequestMethod.GET)
	public ModelAndView accept(@PathVariable final Request request) {
		ModelAndView result;

		this.requestService.accept(request);

		result = new ModelAndView("redirect: /user/request/" + request.getAssociation().getId() + "/list.do");
		result.addObject("flashMessage", "request.accepted");

		return result;
	}

	@RequestMapping(value = "/{request}/deny", method = RequestMethod.GET)
	public ModelAndView deny(@PathVariable final Request request) {
		ModelAndView result;

		this.requestService.deny(request);

		result = new ModelAndView("redirect: /user/request/" + request.getAssociation().getId() + "/list.do");
		result.addObject("flashMessage", "request.denied");

		return result;
	}

}