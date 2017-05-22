
package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import services.AssociationService;
import controllers.AbstractController;

@Controller
public class DashboardAdminController extends AbstractController {

	public DashboardAdminController() {
		super();
	}


	@Autowired
	private AssociationService	associationService;
}
