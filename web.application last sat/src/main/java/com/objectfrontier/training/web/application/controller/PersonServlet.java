package com.objectfrontier.training.web.application.controller;

import java.util.List;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.objectfrontier.training.web.application.model.Person;
import com.objectfrontier.training.web.application.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    PersonService personService;

    @RequestMapping(method = RequestMethod.PUT)
    protected ResponseEntity<Person> doPut(@RequestBody Person person) {
        Person createdPerson = personService.create(person);
        return new ResponseEntity<> (createdPerson, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ResponseEntity<Person> doPost(@RequestBody Person person) {
        Person updatedPerson = personService.update(person);
        return new ResponseEntity<> (updatedPerson, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<Person> doGet(@RequestBody Person person, @PathVariable("id, includeAddress") long id, boolean includeAddress) {
        Person readPerson = personService.read(id, includeAddress);
        return new ResponseEntity<> (readPerson, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<List<Person>> doGetAll(@RequestBody Person person, boolean includeAddress) {
        List<Person> persons = personService.readAll(includeAddress);
        return new ResponseEntity<> (persons, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    protected ResponseEntity<Person> doDelete(@RequestBody Person person, @PathVariable("id") long id) {
        personService.delete(id);
        return new ResponseEntity<> (HttpStatus.OK);
    }
}
