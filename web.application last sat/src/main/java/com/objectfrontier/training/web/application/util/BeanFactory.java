package com.objectfrontier.training.web.application.util;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.objectfrontier.training.web.application.controller.AddressServlet;
import com.objectfrontier.training.web.application.controller.PersonServlet;
import com.objectfrontier.training.web.application.model.Address;
import com.objectfrontier.training.web.application.model.Person;
import com.objectfrontier.training.web.application.service.AddressService;
import com.objectfrontier.training.web.application.service.PersonService;

public class BeanFactory {

    static Resource resource=new ClassPathResource("applicationContext.xml");
    static XmlBeanFactory factory=new XmlBeanFactory(resource);

    public static Address getAddress() {
        return (Address) factory.getBean("address");
    }

    public static Person getPerson() {
        return (Person) factory.getBean("person");
    }

    public static PersonService getPersonService() {
        return (PersonService) factory.getBean("personService");
    }

    public static AddressService getAddressService() {
        return (AddressService) factory.getBean("addressService");
    }

    public static AddressServlet getAddressServlet() {
        return (AddressServlet) factory.getBean("addressServlet");
    }

    public static PersonServlet getPersonServlet() {
        return (PersonServlet) factory.getBean("personServlet");
    }
}
