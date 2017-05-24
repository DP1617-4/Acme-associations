
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;
import services.RolesService;
import services.SanctionService;
import domain.Association;
import domain.Roles;
import domain.Sanction;

@Controller
@RequestMapping("/sanction/user")
public class SanctionUserController extends AbstractController {

	//Constructor

	public SanctionUserController() {
		super();
	}


	//Service

	@Autowired
	private SanctionService	sanctionService;
	
	@Autowired
	private RolesService	rolesService;


	@RequestMapping(value = "/{association}/listByUser", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association, final int userId) {
		ModelAndView result;

		final Collection<Sanction> sanctions = this.sanctionService.findByAssociationAndUser(association, userId);
		final Roles role = this.rolesService.findRolesByPrincipalAssociation(association);

		result = new ModelAndView("sanction/list");
		result.addObject("sanctions", sanctions);
		result.addObject("role", role);
		result.addObject("userId", userId);
		result.addObject("requestURI", "/sanction/user/" + association.getId() + "/listByUser.do?userId="+userId);

		return result;
	}
	
	@RequestMapping(value = "/{association}/listByUserActive", method = RequestMethod.GET)
	public ModelAndView listActive(@PathVariable final Association association, final int userId) {
		ModelAndView result;

		final Collection<Sanction> sanctions = this.sanctionService.findByAssociationAndUserActive(association, userId);
		final Roles role = this.rolesService.findRolesByPrincipalAssociation(association);

		result = new ModelAndView("sanction/list");
		result.addObject("sanctions", sanctions);
		result.addObject("role", role);
		result.addObject("userId", userId);
		result.addObject("requestURI", "/sanction/user/" + association.getId() + "/listByUserActive.do?userId="+userId);

		return result;
	}
	
	@RequestMapping(value = "/{association}/mySanctions", method = RequestMethod.GET)
	public ModelAndView listOwn(@PathVariable final Association association) {
		ModelAndView result;

		final Collection<Sanction> sanctions = this.sanctionService.findByAssociationAndPrincipal(association);
		final Roles role = this.rolesService.findRolesByPrincipalAssociation(association);

		result = new ModelAndView("sanction/list");
		result.addObject("sanctions", sanctions);
		result.addObject("role", role);
		result.addObject("requestURI", "/sanction/user/" + association.getId() + "/mySanctions.do");

		return result;
	}
	
	@RequestMapping(value = "/{association}/myActiveSanctions", method = RequestMethod.GET)
	public ModelAndView listOwnActive(@PathVariable final Association association) {
		ModelAndView result;

		final Collection<Sanction> sanctions = this.sanctionService.findByAssociationAndPrincipalActive(association);
		final Roles role = this.rolesService.findRolesByPrincipalAssociation(association);

		result = new ModelAndView("sanction/list");
		result.addObject("sanctions", sanctions);
		result.addObject("role", role);
		result.addObject("requestURI", "/sanction/user/" + association.getId() + "/myActiveSanctions.do");

		return result;
	}
	
	@RequestMapping(value = "/{association}/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Association association, final int userId) {
		ModelAndView result;
		
		final Sanction newSanction = this.sanctionService.create(userId, association);
		result = this.createEditModelAndView(newSanction, association);
		return result;
	}
	
	@RequestMapping(value = "/{association}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable final Association association, @RequestParam final int sanctionId, final RedirectAttributes redir) {
		ModelAndView result;
		final Sanction sanction;
		try {
			sanction = this.sanctionService.findOneToEdit(sanctionId);

			result = this.createEditModelAndView(sanction, association);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			redir.addFlashAttribute("errorMessage", oops.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/{association}/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Sanction newSanction, final BindingResult binding, @PathVariable final Association association) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(newSanction, association);
		else
			try {
				newSanction = this.sanctionService.save(newSanction);
				result = new ModelAndView("redirect:/sanction/user/" + association.getId() + "/listByUser.do?userId="+newSanction.getUser().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(newSanction, association, "sanction.commit.error");
			}
		return result;
	}
	
	protected ModelAndView createEditModelAndView(final Sanction sanction, final Association association) {
		ModelAndView result;

		result = this.createEditModelAndView(sanction, association, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Sanction sanction, final Association association, final String message) {
		ModelAndView result;

		final String requestURI = "sanction/user/" + association.getId() + "/edit.do";
		final String cancelURI = "sanction/user/" + association.getId() + "/listByUserActive.do?userId="+sanction.getUser().getId();
		final Roles role = this.rolesService.findRolesByPrincipalAssociation(association);
		
		result = new ModelAndView("sanction/edit");
		result.addObject("sanction", sanction);
		result.addObject("role", role);
		result.addObject("errorMessage", message);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", cancelURI);

		return result;
	}

}
