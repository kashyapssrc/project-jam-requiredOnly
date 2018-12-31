package com.objectfrontier.training.web.application.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.objectfrontier.training.web.application.model.Address;
import com.objectfrontier.training.web.application.model.Person;
import com.objectfrontier.training.web.application.util.AppException;
import com.objectfrontier.training.web.application.util.ConnectionManager;
import com.objectfrontier.training.web.application.util.ErrorCodes;
import com.objectfrontier.training.web.application.util.QueryManager;

@Service
public class PersonService {

    @Autowired
    private AddressService addressService;
    public PersonService (AddressService addrezzService) {
        this.addressService = addrezzService;
    }

    private void validate(Person person) {

        List<ErrorCodes> errors = new ArrayList<>();

        if (isEmpty(person.getFirstName())) {
            errors.add(ErrorCodes.INVALID_FIRST_NAME);
        }

        if (isEmpty(person.getLastName())) {
            errors.add(ErrorCodes.INVALID_LAST_NAME);
        }

        if (isEmpty(person.getEmail())) {
            errors.add(ErrorCodes.INVALID_EMAIL);
        }

        if (person.getBirthDate() == null) {
            errors.add(ErrorCodes.INVALID_BIRTH_DATE);
        }

        try {
            validateName(person);
        } catch (AppException e) {
            errors.addAll(e.getErrorCodes());
        }

        try {
            validateEmail(person);
        } catch (AppException e) {
            errors.addAll(e.getErrorCodes());
        }

        if (errors.size() > 0) {
            throw new AppException(errors);
        }
    }

    private boolean isEmpty(String value) {
        return Objects.isNull(value) || "".equals(value);
    }

    private void validateName(Person person) {

        Connection conn = ConnectionManager.getConnection();
        try {
            String query = QueryManager.COUNT_PERSON_BY_NAME;
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            ResultSet executeQuery = statement.executeQuery();
            executeQuery.next();
            int count = executeQuery.getInt(1);
            if (count > 0) {
                List<ErrorCodes> errors = new ArrayList<>();
                errors.add(ErrorCodes.INVALID_NAME);
                throw new AppException(errors);
            }
        } catch (SQLException exception) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, exception.getCause());
        }
    }

    private void validateEmail(Person person) {

        Connection conn = ConnectionManager.getConnection();
        try {
            String query = QueryManager.COUNT_PERSON_BY_EMAIL;
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, person.getEmail());
            ResultSet executeQuery = statement.executeQuery();
            executeQuery.next();
            int count = executeQuery.getInt(1);
            if (count > 0) {
                List<ErrorCodes> errors = new ArrayList<>();
                errors.add(ErrorCodes.DUPLICATE_EMAIL);
                throw new AppException(errors);
            }
        } catch (SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
    }

    private void validateId(long id) {

        if (id == 0) {
            List<ErrorCodes> errors = new ArrayList<>();
            errors.add(ErrorCodes.INVALID_PERSON_ID);
            throw new AppException(errors);
        }
    }

    private void constructPerson(Person person, ResultSet result) {

        try {
            person.setId(result.getLong("id"));
            person.setFirstName(result.getString("first_name"));
            person.setLastName(result.getString("last_name"));
            person.setEmail(result.getString("email"));
            person.setBirthDate(result.getDate("birth_date"));
        } catch (SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
    }

    private void setValue(Person person, PreparedStatement statement) {

        try {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.setDate(4, person.getBirthDate());
            statement.setString(5, person.getPassword());
            statement.setBoolean(6, person.isAdmin());
        } catch (Exception e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
    }

    public Person create(Person person) {

        long generatedKey = 0;
        String insertQuery = QueryManager.CREATE_PERSON;
        Connection conn = ConnectionManager.getConnection();

        try {
            validate(person);

            PreparedStatement statement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            Address address = this.addressService.create(person.getAddress());
            setValue(person, statement);
            person.setAddress(address);
            statement.setLong(7, person.getAddress().getId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if ((generatedKeys != null) && (generatedKeys.next())) {
                generatedKey = generatedKeys.getLong(1);
            }
            person.setId(generatedKey);
        } catch (SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
        return person;
    }

    public Person update(Person person) {

        String query = QueryManager.UPDATE_PERSON;
        Connection conn = ConnectionManager.getConnection();

        try {
            validateId(person.getId());

            PreparedStatement statement = conn.prepareStatement(query);
            Address address = this.addressService.update(person.getAddress());
            setValue(person, statement);
            person.setAddress(address);
            statement.setLong(7, person.getAddress().getId());
            statement.setLong(8, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
        return person;
    }

    public Person read(long id, boolean includeAddress) {

        String readQuery = QueryManager.READ_PERSON;
        Connection conn = ConnectionManager.getConnection();
        Person person = new Person();
        try {

            validateId(id);
            PreparedStatement statement = conn.prepareStatement(readQuery);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                constructPerson(person, rs);
                if (includeAddress) {
                    Address address = new Address();
                    address.setId(rs.getLong("address_id"));
                    Address readAddress = this.addressService.read(address.getId());
                    person.setAddress(readAddress);
                }
            }
        } catch (SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
        return person;
    }

    public List<Person> readAll(boolean includeAddress) {

        String readAllQuery = QueryManager.READ_ALL_PERSON;
        Connection conn = ConnectionManager.getConnection();
        List<Person> resultRecord = new ArrayList<>();
        Person person = new Person();
        try {

            ArrayList<Address> addresses = this.addressService.readAll();
            Map<Long, Address> addressMapper = new HashMap<>();
            for (Address address : addresses) {
                addressMapper.put(address.getId(), address);
            }
            PreparedStatement statement = conn.prepareStatement(readAllQuery);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                constructPerson(person, result);

                if (includeAddress) {
                    Address address = new Address();
                    address.setId(result.getLong("address_id"));
                    person.setAddress(address);
                    person.setAddress(addressMapper.get(person.getAddress().getId()));
                }
                resultRecord.add(person);
            }
        } catch (SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
        return resultRecord;
    }

    public void delete(long id) {

        String deleteQuery = QueryManager.DELETE_PERSON;
        Connection conn = ConnectionManager.getConnection();

        try {
            validateId(id);
            boolean includeAddress = true;
            Person person = read(id, includeAddress);
            PreparedStatement statement = conn.prepareStatement(deleteQuery.toString());
            statement.setLong(1, id);
            statement.executeUpdate();
            this.addressService.delete(person.getAddress().getId());
        } catch (SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
    }
}
