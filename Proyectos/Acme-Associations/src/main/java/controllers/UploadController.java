
package controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/upload")
public class UploadController extends AbstractController {

	//Save the uploaded file to this folder
	private static String	UPLOADED_FOLDER	= "C://UploadedFiles//";


	@RequestMapping(value = "/subir", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView result;
		result = new ModelAndView("upload/upload");
		return result;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView singleFileUpload(@RequestParam("file") final MultipartFile file, final RedirectAttributes redirectAttributes) {

		final ModelAndView result;

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			result = new ModelAndView("upload/status");
			return result;
		}

		try {

			// Get the file and save it somewhere
			final byte[] bytes = file.getBytes();
			final Path path = Paths.get(UploadController.UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (final IOException e) {
			e.printStackTrace();
		}

		result = new ModelAndView("upload/status");
		return result;
	}

	@RequestMapping(value = "/uploadStatus", method = RequestMethod.GET)
	public ModelAndView uploadStatus() {
		ModelAndView result;
		result = new ModelAndView("upload/status");
		return result;
	}

	@RequestMapping(value = "/uploadMulti", method = RequestMethod.POST)
	public String multiFileUpload(@RequestParam("files") final MultipartFile[] files, final RedirectAttributes redirectAttributes) {

		final StringJoiner sj = new StringJoiner(" , ");

		for (final MultipartFile file : files) {

			if (file.isEmpty())
				continue; //next pls

			try {

				final byte[] bytes = file.getBytes();
				final Path path = Paths.get(UploadController.UPLOADED_FOLDER + file.getOriginalFilename());
				Files.write(path, bytes);

				sj.add(file.getOriginalFilename());

			} catch (final IOException e) {
				e.printStackTrace();
			}

		}

		final String uploadedFileName = sj.toString();
		if (StringUtils.isEmpty(uploadedFileName))
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
		else
			redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + uploadedFileName + "'");

		return "redirect:/uploadStatus";

	}

	@RequestMapping(value = "/uploadMultiPage", method = RequestMethod.GET)
	public String uploadMultiPage() {
		return "uploadMulti";
	}

}
