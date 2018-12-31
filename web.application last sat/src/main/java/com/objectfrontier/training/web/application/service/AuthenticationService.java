package com.objectfrontier.training.web.application.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;

import com.objectfrontier.training.web.application.model.Person;
import com.objectfrontier.training.web.application.util.AppException;
import com.objectfrontier.training.web.application.util.BeanFactory;
import com.objectfrontier.training.web.application.util.ConnectionManager;
import com.objectfrontier.training.web.application.util.ErrorCodes;
import com.objectfrontier.training.web.application.util.QueryManager;

public class AuthenticationService {

    public Person login(String email, String password) {
        String query = QueryManager.GET_PERSON_PASSWORD_BY_EMAIL;
        Person person = BeanFactory.getPerson();
        ArrayList<ErrorCodes> errors = new ArrayList<>();

        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            while (result.next()) {

                person.setId(result.getLong("id"));
                person.setEmail(result.getString("email"));
                person.setAdmin(result.getBoolean("is_admin"));
            }

            if (Objects.isNull(person.getId())) {

                errors.add(ErrorCodes.INVALID_CREDENTIALS);
                throw new AppException(errors);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errors.add(ErrorCodes.CONNECTION_ERROR);
            throw new AppException(errors);
        }
        return person;
    }}
