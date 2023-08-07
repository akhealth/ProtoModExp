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
public class ClientSearchController {

	// we autowire model repositories so that the web controller can access the methods needed to access db
	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@RequestMapping(value = "/clientsearch", method = RequestMethod.GET)
	public String showClientSearchPage(ModelMap model, RedirectAttributes redirectAttributes) {
		return "clientsearch";
	}
	
	@RequestMapping(value = "/clientsearch", method = RequestMethod.POST, params = "submit")
	public ModelAndView submitClientSearch(
			@RequestParam("clientId") String clientId,
			@RequestParam("lastName") String lastName,
			@RequestParam("firstName") String firstName,				
			@RequestHeader HttpHeaders headers,	
			ModelMap model,
			RedirectAttributes redirectAttributes) {
		
		List<Person> clients = new ArrayList<Person>();
		
		try {
			Optional<Person> personData = personRepository.findById(Long.parseLong(clientId));
			clients.add(personData.get());
		}
		catch(Exception e){
			
		}

        model.addAttribute("clients", clients);

        return new ModelAndView("clientsearch", model);
	}
	

	@RequestMapping(value = "/clientdetails", method = RequestMethod.GET)
	public String showClientDetailsPage(
			@RequestParam(name="clientId", required=false, defaultValue="default ID") 
			String clientId,
			ModelMap model) {
		
		// value redirected from the search page
		model.addAttribute("clientId", clientId);
		
		Optional<Person> personData = personRepository.findById(Long.parseLong(clientId));
		
		model.addAttribute("clientId", personData.get().getAries_id());
		model.addAttribute("lastName", personData.get().getLast_name());
		model.addAttribute("firstName", personData.get().getFirst_name());
		model.addAttribute("dateOfBirth", personData.get().getDob());
		model.addAttribute("personData",personData.get());
		
		return "clientdetails";
	}
	
	@RequestMapping(value = "/clientdetails", method = RequestMethod.GET, params = "search")
	public String clientDetailsSearchBar(
			ModelMap model) {
		
		return "clientsearch";
	}
	
	@RequestMapping(value = "/clientdetails", method = RequestMethod.POST, params = "edit")
	public String submitClientDetailsChange(
			@ModelAttribute("clientId") String clientId,
			@ModelAttribute("lastName") String lastName,
			@ModelAttribute("firstName") String firstName, 
			@ModelAttribute("dateOfBirth") String dateOfBirth,
			@ModelAttribute("personData") Person personData,	
			@RequestHeader HttpHeaders headers,	
			ModelMap model)  {
			
			try {
				personData.setFirst_name(firstName);
				personData.setAries_id(Long.parseLong(clientId));
				personData.setLast_name(lastName);
				personData.setSsn("testSSN");
				
				personRepository.save(personData);
			}
			catch(Exception e){
				
			}
			

		return "clientdetails";
	}
}