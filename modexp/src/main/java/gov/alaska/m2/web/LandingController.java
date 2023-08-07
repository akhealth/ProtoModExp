package gov.alaska.m2.web;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.alaska.m2.api.model.*;
import gov.alaska.m2.api.repository.*;


@Controller
public class LandingController {

	// we autowire model repositories so that the web controller can access the methods needed to access db
	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@RequestMapping(value = "/landingpage", method = RequestMethod.GET)
	public String loginPage(ModelMap model, RedirectAttributes redirectAttributes) {
		return "landingpage";
	}
	
	@RequestMapping(value = "/module", method = RequestMethod.GET)
	public String showModulePage(
			@RequestParam(name="moduleName", 
			required=false, defaultValue="Worker Portal") String moduleName,
			ModelMap model, 
			RedirectAttributes redirectAttributes) {
		
			model.addAttribute("moduleName", moduleName);
		
		return "module";
	}
}