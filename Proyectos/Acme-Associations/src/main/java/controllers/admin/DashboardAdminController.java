
package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.AssociationService;
import services.LoanService;
import services.UserService;
import controllers.AbstractController;
import domain.Activity;
import domain.Association;
import domain.User;

@Controller
@RequestMapping("/dashboard/admin")
public class DashboardAdminController extends AbstractController {

	public DashboardAdminController() {
		super();
	}


	@Autowired
	private AssociationService	associationService;

	@Autowired
	private UserService			userService;

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private LoanService			loanService;


	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Association> assoAvG = this.associationService.findAssociationsAroundAVGMembers();
		final Collection<Association> assoLoan = this.associationService.findMostLoansAssociation();
		final Collection<Association> assoInac = this.associationService.inactiveAssociations();
		final Association association = this.associationService.findOrderedBySanctionsDesc();
		final Object[] avgMinMaxMembers = this.userService.minMaxAvgMembers();
		final Collection<User> mostSanctionedUsers = this.userService.mostSanctionedUsers();
		final Collection<Activity> activeActivities = this.activityService.activeActivitiesWithMostUsers();
		final Object[] avgMinMaxLoans = this.loanService.minMaxAvgLoans();

		result = new ModelAndView("dashboard/admin");
		result.addObject("assoAVG", assoAvG);
		result.addObject("assoLoan", assoLoan);
		result.addObject("assoInac", assoInac);
		result.addObject("sanctionator", association);
		result.addObject("avgMembers", avgMinMaxMembers[0]);
		result.addObject("minMembers", avgMinMaxMembers[1]);
		result.addObject("maxMembers", avgMinMaxMembers[2]);
		result.addObject("sanctionated", mostSanctionedUsers);
		result.addObject("activeties", activeActivities);
		result.addObject("avgLoans", avgMinMaxLoans[0]);
		result.addObject("minLoans", avgMinMaxLoans[1]);
		result.addObject("maxLoans", avgMinMaxLoans[2]);
		/*
		 * El m�nimo, el m�ximo y la media de miembros por asociaci�n.
		 * Las asociaciones que tengan +-10% de la media del n�mero de productos.
		 * Asociaciones con m�s prestamos en el �ltimo mes.
		 * El m�nimo, el m�ximo y la media de pr�stamos por asociaci�n.
		 * Asociaciones que m�s han sancionado.
		 * Asociaciones inactivas m�s de 3 meses.
		 * Usuarios con m�s sanciones.
		 * Actividades activas con m�s usuarios apuntados.
		 */
		return result;
	}
}
