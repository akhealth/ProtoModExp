package gov.alaska.m2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.alaska.m2.api.repository.*;

@Controller
public class LoginController {

	// we autowire model repositories so that the web controller can access the
	// methods needed to access db
	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	PersonRepository personRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultPage(ModelMap model, RedirectAttributes redirectAttributes) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(ModelMap model, RedirectAttributes redirectAttributes) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, params = "submit")
	public ModelAndView submitLoginData(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			ModelMap model,
			RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("username", username);

		return new ModelAndView("redirect:/landingpage", model);
	}

}