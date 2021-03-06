package com.journaldev.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journaldev.spring.model.Person;
import com.journaldev.spring.service.PersonService;

@Controller
public class PersonController {

	@Autowired(required=true)
	private PersonService personService;
	

//	@Qualifier(value="personService")
	public void setPersonService(final PersonService ps){
		this.personService = ps;
	}
	 
	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public String listPersons(final Model model) {
		model.addAttribute("person", new Person());
		model.addAttribute("listPersons", this.personService.listPersons());
		return "person";
	}
	
	//For add and update person both
	@RequestMapping(value= "/person/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("person") final Person p){
		
		if(p.getId() == 0){
			//new person, add it
			this.personService.addPerson(p);
		}else{
			//existing person, call update
			this.personService.updatePerson(p);
		}
		
		return "redirect:/person";
		
	}
	
	@RequestMapping("/remove/{id}")
    public String removePerson(@PathVariable("id") final int id){
		
        this.personService.removePerson(id);
        return "redirect:/person";
    }
 
    @RequestMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") final int id, final Model model){
        model.addAttribute("person", this.personService.getPersonById(id));
        model.addAttribute("listPersons", this.personService.listPersons());
        return "person";
    }
	
}