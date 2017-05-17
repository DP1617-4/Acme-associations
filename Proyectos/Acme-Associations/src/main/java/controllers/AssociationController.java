
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AssociationService;
import services.CommentService;
import services.RolesService;
import domain.Association;
import domain.Comment;
import forms.MessageBroadcast;

@Controller
@RequestMapping("/association")
public class AssociationController extends AbstractController {

	//Constructor

	public AssociationController() {
		super();
	}


	//Service

	@Autowired
	private AssociationService	associationService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private RolesService		rolesService;


	@RequestMapping(value = "/{association}/display", method = RequestMethod.GET)
	public ModelAndView display(@PathVariable final Association association) {
		ModelAndView result;
		Collection<Comment> comments;
		comments = this.commentService.findAllByCommentableId(association.getId());
		final MessageBroadcast messageBroad = new MessageBroadcast();
		String role;
		role = this.rolesService.findRolesByPrincipalAssociation(association).getType();

		result = new ModelAndView("association/display");
		result.addObject("association", association);
		result.addObject("comments", comments);
		result.addObject("requestURI", "/association/" + association.getId() + "/display.do");
		result.addObject("messageBroad", messageBroad);
		result.addObject("role", role);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Association> associations = this.associationService.findAllExceptBannedAndClosed();

		result = new ModelAndView("association/list");
		result.addObject("associations", associations);
		result.addObject("requestURI", "/association/list.do");

		return result;
	}
}
