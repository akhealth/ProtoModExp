package gov.alaska.m2.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.alaska.m2.api.model.*;
import gov.alaska.m2.api.repository.*;

@Controller
public class AppSearchController {

	// we autowire model repositories so that the web controller can access the
	// methods needed to access db
	@Autowired
	ApplicationRepository applicationRepository;

	@Autowired
	PersonRepository personRepository;

	@RequestMapping(value = "/applicationsearch", method = RequestMethod.GET)
	public String showApplicationSearch(ModelMap model, RedirectAttributes redirectAttributes) {
		return "applicationsearch";
	}

	@RequestMapping(value = "/applicationsearch", method = RequestMethod.POST, params = "submit")
	public ModelAndView submitClientSearch(
			@RequestParam("applicationId") String applicationId,
			@RequestParam("lastName") String lastName,
			@RequestParam("firstName") String firstName,
			@RequestHeader HttpHeaders headers,
			ModelMap model,
			RedirectAttributes redirectAttributes) {

		List<Application> apps = new ArrayList<Application>();

		try {
			Optional<Application> appData = applicationRepository.findById(applicationId);
			apps.add(appData.get());
		} catch (Exception e) {

		}

		model.addAttribute("applications", apps);
		model.addAttribute("applicationId", applicationId);
		model.addAttribute("lastname", lastName);
		model.addAttribute("firstname", firstName);

		return new ModelAndView("applicationsearch", model);
	}

	@RequestMapping(value = "/applicationdetails", method = RequestMethod.GET)
	public String showClientDetailsPage(
			@RequestParam(name = "appNum", required = false, defaultValue = "default ID") String appNum,
			ModelMap model) {

		model.addAttribute("appNum", appNum);
		Optional<Application> appData = applicationRepository.findById(appNum);

		Person person = appData.get().getHeadOfHousehold();

		model.addAttribute("clientId", person.getAries_id());
		model.addAttribute("lastName", person.getLast_name());
		model.addAttribute("firstName", person.getFirst_name());
		model.addAttribute("dateOfBirth", person.getDob());
		model.addAttribute("personData", person);

		return "applicationdetails";
	}

	@RequestMapping(value = "/applicationdetails", method = RequestMethod.GET, params = "search")
	public String clientDetailsSearchBar(
			ModelMap model) {

		return "applicationsearch";
	}

	@RequestMapping(value = "/applicationdetails", method = RequestMethod.POST, params = "edit")
	public String submitClientDetailsChange(
			@ModelAttribute("appNum") String appNum,
			@ModelAttribute("lastName") String lastName,
			@ModelAttribute("firstName") String firstName,
			@ModelAttribute("dateOfBirth") String dateOfBirth,
			@ModelAttribute("personData") Person personData,
			@RequestHeader HttpHeaders headers,
			ModelMap model) {

		personData.setFirst_name(firstName);
		personData.setAries_id(Long.parseLong(appNum));
		personData.setLast_name(lastName);
		personData.setSsn("testSSN");

		personRepository.save(personData);

		return "applicationdetails";
	}
}