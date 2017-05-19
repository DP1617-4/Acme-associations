
package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.CommentableService;
import domain.Comment;
import domain.Commentable;

@Controller
@RequestMapping("/comment/user")
public class CommentUserController {

	//Services

	@Autowired
	private CommentService		commentService;

	@Autowired
	private CommentableService	commentableService;

	


	//Constructor

	public CommentUserController() {
		super();
	}

	//edit

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int commentableId) {
		ModelAndView result;
		Comment comment;
		final Commentable commentable = this.commentableService.findOne(commentableId);

		try {
			comment = this.commentService.create(commentable);
			result = this.createEditModelAndView(comment);
		} catch (final Exception exc) {

			result = new ModelAndView("redirect:welcome/index.do");
		}

		return result;
	}

	//edit

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Comment comment, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(comment);
		else
			try {
				comment = this.commentService.save(comment);
				result = new ModelAndView("redirect:/comment/user/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "user.commit.error");
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Comment> comment;

		comment = this.commentService.findAllByPrincipal();

		result = new ModelAndView("comment/list");
		result.addObject("comment", comment);
		result.addObject("requestURI", "comment/user/list.do");

		return result;
	}

	//Display		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;

		comment = this.commentService.findOne(commentId);
		result = new ModelAndView("comment/display");

		result.addObject("comment", comment);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;

		final String requestURI = "comment/user/edit.do";

		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
