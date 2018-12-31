package com.objectfrontier.training.web.application.model;

import java.sql.Date;
import java.sql.Time;

public class Person {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private Time createdDate;
    private String password;
    private boolean isAdmin;
    Address address;

    public Person(String firstName, String lastName, String email, Date birthDate, String password, boolean isAdmin, Address address) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.password = password;
        this.isAdmin = isAdmin;
        this.address = address;
    }

    public Person() {
    }

    public Person(long id, String firstName, String lastName, String email, Date birthDate, String password, boolean isAdmin, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.password =password;
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public Time getCreatedDate() {
        return this.createdDate;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setCreatedDate(Time createdDate) {
        this.createdDate = createdDate;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", birthDate=" + birthDate + "]";
        }
}
