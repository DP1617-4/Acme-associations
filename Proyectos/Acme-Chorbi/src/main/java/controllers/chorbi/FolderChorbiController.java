
package controllers.chorbi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import controllers.AbstractController;
import domain.Folder;

@Controller
@RequestMapping("/folder/chorbi")
public class FolderChorbiController extends AbstractController {

	//Services

	@Autowired
	private FolderService	folderService;


	//Constructor

	public FolderChorbiController() {
		super();
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		Collection<Folder> folders;

		folders = this.folderService.findAllByPrincipal();

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	//Needs further testing

}
