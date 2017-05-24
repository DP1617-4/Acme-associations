
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;
import services.RolesService;
import services.SanctionService;
import services.UserService;
import domain.Roles;
import domain.Sanction;

@Controller
@RequestMapping("/sanction")
public class SanctionController extends AbstractController {

	//Constructor

	public SanctionController() {
		super();
	}


	//Service

	@Autowired
	private SanctionService	sanctionService;

	@Autowired
	private RolesService	rolesService;

	@Autowired
	private UserService		userService;


	@RequestMapping(value = "/mySanctions", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Sanction> sanctions = this.sanctionService.findAllByPrincipal();

		result = new ModelAndView("sanction/list");
		result.addObject("sanctions", sanctions);
		result.addObject("requestURI", "/sanction/mySanctions.do");

		return result;
	}
	
	@RequestMapping(value = "/myActiveSanctions", method = RequestMethod.GET)
	public ModelAndView listActive() {
		ModelAndView result;

		final Collection<Sanction> sanctions = this.sanctionService.findAllByPrincipalActive();

		result = new ModelAndView("sanction/list");
		result.addObject("sanctions", sanctions);
		result.addObject("requestURI", "/sanction/myActiveSanctions.do");

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sanctionId, final RedirectAttributes redir) {
		ModelAndView result;
		final Sanction sanction;
		try {
			sanction = this.sanctionService.findOne(sanctionId);

			result = this.createEditModelAndView(sanction);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", oops.getMessage());
		}

		return result;
	}
	
	protected ModelAndView createEditModelAndView(final Sanction sanction) {
		ModelAndView result;

		result = this.createEditModelAndView(sanction, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Sanction sanction, final String message) {
		ModelAndView result;

		final String requestURI = "sanction/user/" + sanction.getAssociation().getId() + "/edit.do";
		String cancelURI = "";
		if(sanction.getUser().getId() == userService.findByPrincipal().getId()){
			cancelURI = "sanction/user/" + sanction.getAssociation().getId() + "/myActiveSanctions.do?userId="+sanction.getUser().getId();
		}else{
			cancelURI = "sanction/user/" + sanction.getAssociation().getId() + "/listByUserActive.do?userId="+sanction.getUser().getId();
		}
		final Roles role = this.rolesService.findRolesByPrincipalAssociation(sanction.getAssociation());
		
		result = new ModelAndView("sanction/edit");
		result.addObject("sanction", sanction);
		result.addObject("role", role);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", cancelURI);

		return result;
	}
	
}
