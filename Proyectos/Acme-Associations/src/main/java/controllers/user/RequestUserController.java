
package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.RequestService;
import controllers.AbstractController;
import domain.Association;
import domain.Request;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {

	public RequestUserController() {
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
		result.addObject("requestURI", "/request/user/list.do");

		return result;
	}

	@RequestMapping(value = "/{association}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association, final RedirectAttributes redir) {
		ModelAndView result;

		try {
			final Collection<Request> requests = this.requestService.findAllByAssociation(association);
			result = new ModelAndView("request/list");
			result.addObject("requests", requests);
		} catch (final Exception exc) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", exc.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/{association}/apply", method = RequestMethod.GET)
	public ModelAndView apply(@PathVariable final Association association, final RedirectAttributes redir) {
		ModelAndView result;

		final Request request = this.requestService.create(association);
		result = new ModelAndView("redirect:/association/" + association.getId() + "/display.do");
		try {
			this.requestService.save(request);
			redir.addFlashAttribute("flashMessage", "request.success");
		} catch (final IllegalArgumentException e) { //Lo suyo sería añadir varios mensajes en el assert del servicio, y se imprime el mensaje (Mensaje con código de int)
			redir.addFlashAttribute("flashMessage", e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/{request}/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@PathVariable final Request request, final RedirectAttributes redir) {
		ModelAndView result;

		this.requestService.delete(request);

		result = new ModelAndView("redirect:/request/user/list.do");
		redir.addFlashAttribute("flashMessage", "request.deleted");

		return result;
	}

	@RequestMapping(value = "/{request}/accept", method = RequestMethod.GET)
	public ModelAndView accept(@PathVariable final Request request, final RedirectAttributes redir) {
		ModelAndView result;
		try {
			this.requestService.accept(request);
			result = new ModelAndView("redirect:request/user/" + request.getAssociation().getId() + "/list.do");
		} catch (final Exception exc) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", exc.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/{request}/deny", method = RequestMethod.GET)
	public ModelAndView deny(@PathVariable final Request request, final RedirectAttributes redir) {
		ModelAndView result;

		try {
			this.requestService.deny(request);
			result = new ModelAndView("redirect:request/user/" + request.getAssociation().getId() + "/list.do");
		} catch (final Exception exc) {

			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", exc.getMessage());
		}

		return result;
	}

}
