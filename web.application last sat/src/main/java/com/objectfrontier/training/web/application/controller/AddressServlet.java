package com.objectfrontier.training.web.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.objectfrontier.training.web.application.model.Address;
import com.objectfrontier.training.web.application.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressServlet {

    @Autowired
    AddressService addressService;

    @RequestMapping(method = RequestMethod.PUT)
    protected ResponseEntity<Address> doPut(@RequestBody Address address) {
        Address createdAddress = addressService.create(address);
        return new ResponseEntity<> (createdAddress, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ResponseEntity<Address> doPost(@RequestBody Address address) {
            Address updatedAddress = addressService.update(address);
            return new ResponseEntity<> (updatedAddress, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<Address> doGet(@RequestBody Address address, @PathVariable("id") long id) {
        Address readAddress = addressService.read(id);
        return new ResponseEntity<> (readAddress, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<List<Address>> doGetAll(@RequestBody Address address) {
        List<Address> addresses = new ArrayList<>();
        addresses = addressService.readAll();
        return new ResponseEntity<> (addresses, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<List<Address>> doSearch(@RequestBody Address address
                                                    , @PathVariable("id, searchInput, searchField") long id
                                                                                                  , String searchInput
                                                                                                  , String searchField) {
        List<Address> searchResult = new ArrayList<>();
        if ((searchField != null) || (searchInput != null)) {
            String[] fields = searchField.split(",");
            searchResult = addressService.search(fields, searchInput);
        }
        return new ResponseEntity<> (searchResult, null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    protected ResponseEntity<Address> doDelete(@RequestBody Address address, @PathVariable("id") long id) {
        addressService.delete(id);
        return new ResponseEntity<> (HttpStatus.NO_CONTENT);
    }
}
