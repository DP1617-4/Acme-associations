
package controllers.chorbi;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.LikesService;
import domain.Chorbi;
import domain.Likes;

@Controller
@RequestMapping("/likes/chorbi")
public class LikesChorbiController {

	//Services

	@Autowired
	private LikesService	likesService;

	@Autowired
	private ChorbiService	chorbiService;


	//Constructor

	public LikesChorbiController() {
		super();
	}

	//edit

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int likedId) {
		ModelAndView result;
		Likes likes;
		final Chorbi liked = this.chorbiService.findOne(likedId);

		likes = this.likesService.create(liked);
		result = this.createEditModelAndView(likes);

		return result;
	}

	//edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int likesId) {
		ModelAndView result;
		Likes likes;

		likes = this.likesService.findByOneToEdit(likesId);
		result = this.createEditModelAndView(likes);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Likes likes, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(likes);
		else
			try {
				likes = this.likesService.save(likes);
				result = new ModelAndView("redirect:/likes/chorbi/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(likes, "chorbi.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int likesId) {
		ModelAndView result;

		final Chorbi liked = this.chorbiService.findOne(likesId);
		final Chorbi principal = this.chorbiService.findByPrincipal();
		Likes likes;

		likes = this.likesService.findOneByChorbiAndLiked(principal.getId(), liked.getId());
		this.likesService.delete(likes);

		result = new ModelAndView("redirect:/likes/chorbi/list.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Likes> likes;

		likes = this.likesService.findAllByPrincipal();

		result = new ModelAndView("likes/list");
		result.addObject("likes", likes);
		result.addObject("requestURI", "likes/chorbi/list.do");

		return result;
	}

	//Display		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int likesId) {
		ModelAndView result;
		Likes likes;

		likes = this.likesService.findOne(likesId);
		result = new ModelAndView("likes/display");

		result.addObject("likes", likes);

		return result;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Likes likes) {
		ModelAndView result;

		result = this.createEditModelAndView(likes, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Likes likes, final String message) {
		ModelAndView result;

		final String requestURI = "likes/chorbi/edit.do";

		result = new ModelAndView("likes/edit");
		result.addObject("likes", likes);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}

}
