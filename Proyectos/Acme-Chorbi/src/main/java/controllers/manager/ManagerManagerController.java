
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import domain.Manager;

@Controller
@RequestMapping("/_manager/_manager")
public class ManagerManagerController {

	//Services

	@Autowired
	private ManagerService	managerService;


	//Constructor

	public ManagerManagerController() {
		super();
	}

	//Register

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Manager manager;

		manager = this.managerService.findByPrincipal();
		result = this.createEditModelAndView(manager);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;

		Manager manager;

		manager = this.managerService.findByPrincipal();

		result = new ModelAndView("manager/display");
		result.addObject("manager", manager);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Manager editManager, final BindingResult binding) {
		ModelAndView result;
		Manager manager;
		manager = editManager;

		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				manager = this.managerService.save(manager);
				result = new ModelAndView("redirect:/_manager/_manager/display.do?managerId=" + manager.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(manager, "manager.commit.error");
			}
		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Manager manager) {
		ModelAndView result;

		result = this.createEditModelAndView(manager, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Manager manager, final String message) {
		ModelAndView result;

		final String requestURI = "_manager/_manager/edit.do";

		result = new ModelAndView("manager/edit");
		result.addObject("manager", manager);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
