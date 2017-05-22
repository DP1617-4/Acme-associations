
package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CommentService;
import services.CommentableService;
import services.RolesService;
import domain.Association;
import domain.Comment;
import domain.Commentable;
import domain.Item;
import domain.Meeting;
import domain.Minutes;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController {

	//Services

	@Autowired
	private CommentService		commentService;

	@Autowired
	private CommentableService	commentableService;

	@Autowired
	private RolesService		rolesService;


	//Constructor

	public CommentUserController() {
		super();
	}

	//edit

	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create(@RequestParam final int commentableId) {
	//		ModelAndView result;
	//		Comment comment;
	//		final Commentable commentable = this.commentableService.findOne(commentableId);
	//
	//		try {
	//			comment = this.commentService.create(commentable);
	//			result = this.createEditModelAndView(comment);
	//		} catch (final Exception exc) {
	//
	//			result = new ModelAndView("redirect:welcome/index.do");
	//		}
	//
	//		return result;
	//	}

	//edit

	@RequestMapping(value = "{commentable}/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Comment comment, final BindingResult binding, @PathVariable final Commentable commentable, final RedirectAttributes redir) {
		ModelAndView result;

		result = new ModelAndView("redirect:/welcome/index.do");

		if (binding.hasErrors()) {
			if (commentable instanceof Association)
				result = new ModelAndView("redirect:/association/" + commentable.getId() + "/display.do");
			if (commentable instanceof Item)
				result = new ModelAndView("redirect:/item/user/" + ((Item) commentable).getSection().getAssociation().getId() + "/display.do?itemId=" + commentable.getId());
			if (commentable instanceof Meeting || commentable instanceof Minutes)
				result = new ModelAndView("redirect:/meeting/user/" + ((Meeting) commentable).getAssociation() + "/" + commentable.getId() + "/display.do");
		} else
			try {
				comment = this.commentService.reconstruct(comment, binding);
				this.commentService.checkPrincipalCanComment(commentable);
				this.commentService.save(comment);
				result = new ModelAndView("redirect:/welcome/index.do");
				if (commentable instanceof Association)
					result = new ModelAndView("redirect:/association/" + commentable.getId() + "/display.do");
				if (commentable instanceof Item)
					result = new ModelAndView("redirect:/item/user/" + ((Item) commentable).getSection().getAssociation().getId() + "/display.do?itemId=" + commentable.getId());
				if (commentable instanceof Meeting) {
					final Meeting meeting = (Meeting) commentable;
					result = new ModelAndView("redirect:/meeting/user/" + meeting.getAssociation().getId() + "/" + commentable.getId() + "/display.do");
				}
				if (commentable instanceof Meeting) {
					final Minutes minutes = (Minutes) commentable;
					final Meeting meeting = minutes.getMeeting();
					result = new ModelAndView("redirect:/meeting/user/" + meeting.getAssociation().getId() + "/" + meeting.getId() + "/display.do");
				}
			} catch (final Throwable oops) {
				if (commentable instanceof Association)
					result = new ModelAndView("redirect:/association/" + commentable.getId() + "/display.do");
				if (commentable instanceof Item)
					result = new ModelAndView("redirect:/item/user/" + ((Item) commentable).getSection().getAssociation().getId() + "/display.do?itemId=" + commentable.getId());
				if (commentable instanceof Meeting || commentable instanceof Minutes)
					result = new ModelAndView("redirect:/meeting/user/" + ((Meeting) commentable).getAssociation() + "/" + commentable.getId() + "/display.do");
			}
		return result;
	}
	@RequestMapping(value = "{commentable}/editSecond", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSecond(Comment commentSecond, final BindingResult binding, @PathVariable final Commentable commentable) {
		ModelAndView result;

		result = new ModelAndView("redirect:/welcome/index.do");

		if (binding.hasErrors()) {
			if (commentable instanceof Association)
				result = new ModelAndView("redirect:/association/" + commentable.getId() + "/display.do");
			if (commentable instanceof Item)
				result = new ModelAndView("redirect:/item/user/" + ((Item) commentable).getSection().getAssociation().getId() + "/display.do?itemId=" + commentable.getId());
			if (commentable instanceof Meeting || commentable instanceof Minutes)
				result = new ModelAndView("redirect:/meeting/user/" + ((Meeting) commentable).getAssociation() + "/" + commentable.getId() + "/display.do");
		} else
			try {
				commentSecond = this.commentService.reconstruct(commentSecond, binding);
				this.commentService.checkPrincipalCanComment(commentable);
				this.commentService.save(commentSecond);
				result = new ModelAndView("redirect:/welcome/index.do");
				if (commentable instanceof Association)
					result = new ModelAndView("redirect:/association/" + commentable.getId() + "/display.do");
				if (commentable instanceof Item)
					result = new ModelAndView("redirect:/item/user/" + ((Item) commentable).getSection().getAssociation().getId() + "/display.do?itemId=" + commentable.getId());
				if (commentable instanceof Meeting || commentable instanceof Minutes)
					result = new ModelAndView("redirect:/meeting/user/" + ((Meeting) commentable).getAssociation() + "/" + commentable.getId() + "/display.do");

			} catch (final Throwable oops) {
				if (commentable instanceof Association)
					result = new ModelAndView("redirect:/association/" + commentable.getId() + "/display.do");
				if (commentable instanceof Item)
					result = new ModelAndView("redirect:/item/user/" + ((Item) commentable).getSection().getAssociation().getId() + "/display.do?itemId=" + commentable.getId());
				if (commentable instanceof Meeting || commentable instanceof Minutes)
					result = new ModelAndView("redirect:/meeting/user/" + ((Meeting) commentable).getAssociation() + "/" + commentable.getId() + "/display.do");
				redir.addFlashAttribute("flashMessage", oops.getMessage());
			}
		return result;
	}
	//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	//	public ModelAndView delete(@RequestParam final int commentId) {
	//		ModelAndView result;
	//
	//		final User liked = this.userService.findOne(commentId);
	//		final User principal = this.userService.findByPrincipal();
	//		Comment comment;
	//
	//		comment = this.commentService.findOneByUserAndLiked(principal.getId(), liked.getId());
	//		this.commentService.delete(comment);
	//
	//		result = new ModelAndView("redirect:/comment/user/list.do");
	//
	//		return result;
	//	}

	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
	//		ModelAndView result;
	//
	//		Collection<Comment> comment;
	//
	//		comment = this.commentService.findAllByPrincipal();
	//
	//		result = new ModelAndView("comment/list");
	//		result.addObject("comment", comment);
	//		result.addObject("requestURI", "comment/user/list.do");
	//
	//		return result;
	//	}

	//Display		
	//	@RequestMapping(value = "/display", method = RequestMethod.GET)
	//	public ModelAndView display(@RequestParam final int commentId) {
	//		ModelAndView result;
	//		Comment comment;
	//
	//		comment = this.commentService.findOne(commentId);
	//		result = new ModelAndView("comment/display");
	//
	//		result.addObject("comment", comment);
	//
	//		return result;
	//	}

	// Ancillary methods

}
