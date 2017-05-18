
package controllers.user;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.LoanService;
import services.RolesService;
import services.UserService;
import controllers.AbstractController;
import domain.Association;
import domain.Item;
import domain.Loan;
import domain.Roles;
import domain.User;

@Controller
@RequestMapping("/loan/user")
public class LoanUserController extends AbstractController {

	public LoanUserController() {
		super();
	}


	//Services

	@Autowired
	private LoanService		loanService;

	@Autowired
	private UserService		userService;

	@Autowired
	private RolesService	rolesService;


	@RequestMapping(value = "/{association}/list", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable final Association association) {
		ModelAndView result;

		final List<Loan> loans = this.loanService.findByAssociation(association);
		final Roles role = this.rolesService.findRolesByPrincipalAssociation(association);
		result = new ModelAndView("loan/list");
		result.addObject("loans", loans);
		result.addObject("association", association);
		result.addObject("role", role);

		return result;
	}

	@RequestMapping(value = "/{association}/listPending", method = RequestMethod.GET)
	public ModelAndView listPending(@PathVariable final Association association) {
		ModelAndView result;

		final List<Loan> loans = this.loanService.findPendingByAssociation(association);
		final Roles role = this.rolesService.findRolesByPrincipalAssociation(association);
		result = new ModelAndView("loan/list");
		result.addObject("loans", loans);
		result.addObject("association", association);
		result.addObject("role", role);

		return result;
	}

	@RequestMapping(value = "/{association}/{item}/create", method = RequestMethod.GET)
	public ModelAndView create(@PathVariable final Association association, @PathVariable final Item item, final RedirectAttributes redir) {
		ModelAndView result;
		try {
			this.rolesService.checkCollaboratorPrincipal(association);
			final Loan loan = this.loanService.create();
			loan.setItem(item);
			result = this.createEditModelAndView(association, item, loan, null);
		} catch (final IllegalArgumentException e) {
			result = new ModelAndView("redirect:/association/" + association.getId() + "/display.do");
			redir.addFlashAttribute("flashMessage", "association.role.collaborator.error");
		}
		return result;
	}

	@RequestMapping(value = "/{association}/{item}/create", method = RequestMethod.POST)
	public ModelAndView save(@PathVariable final Association association, @PathVariable final Item item, Loan loan, final BindingResult binding, final RedirectAttributes redir) {
		ModelAndView result;

		this.loanService.reconstruct(loan, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(association, item, loan, null);
		else
			try {
				loan = this.loanService.save(loan);
				result = new ModelAndView("redirect:/loan/user/" + association.getId() + "/list.do");

			} catch (final IllegalArgumentException e) {
				result = new ModelAndView("redirect:/welcome/index.do");
				redir.addFlashAttribute("errorMessage", e.getMessage());
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Association association, final Item item, final Loan loan, final String message) {
		ModelAndView result;

		final String requestURI = "/loan/user/" + association.getId() + "/" + item.getId() + "/create.do";
		final String cancelURI = "/association/" + association.getId() + "/display.do";
		final Collection<User> users = this.userService.findAllByAssociation(association);
		result = new ModelAndView("loan/edit");
		result.addObject("loan", loan);
		result.addObject("users", users);
		result.addObject("requestURI", requestURI);
		result.addObject("cancelURI", cancelURI);
		result.addObject("errorMessage", message);

		return result;
	}

}
