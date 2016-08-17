package com.journaldev.spring;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.journaldev.spring.model.Person;
import com.journaldev.spring.service.PersonService;

@ContextConfiguration(locations = { "/servlet-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PersonControllerTest {

	@Autowired(required = true)
	private PersonService personService;

	@Test
	public void listPersons() {
		try {
			final List<Person> lst = personService.listPersons();
			assertThat(lst.isEmpty(), is(false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}