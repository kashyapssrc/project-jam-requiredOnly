package com.objectfrontier.training.web.application.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.objectfrontier.training.web.application.model.Address;
import com.objectfrontier.training.web.application.util.AppException;
import com.objectfrontier.training.web.application.util.ConnectionManager;
import com.objectfrontier.training.web.application.util.ErrorCodes;
import com.objectfrontier.training.web.application.util.QueryManager;

@Service
public class AddressService implements QueryManager {

    private void validate(Address address) {

        List<ErrorCodes> errors = new ArrayList<>();

        if (isEmpty(address.getStreet())) {
            errors.add(ErrorCodes.INVALID_STREET);
        };

        if (isEmpty(address.getCity())) {
            errors.add(ErrorCodes.INVALID_CITY);
        };

        if (address.getPincode() == 0) {
            errors.add(ErrorCodes.INVALID_PINCODE);
        };

        if (errors.size() > 0) {
            throw new AppException(errors);
        }
    }

    private boolean isEmpty(String value) {
        return Objects.isNull(value) || "".equals(value);
    }

    private void validateId(long id, Connection conn) {

        if (id == 0) {
            ArrayList<ErrorCodes> errors = new ArrayList<>();
            errors.add(ErrorCodes.INVALID_ADDRESS_ID);
            throw new AppException(errors);
        }
    }

    private void validateValue(String searchValue) {
        if (isEmpty(searchValue)) {
            ArrayList<ErrorCodes> errors = new ArrayList<>();
            errors.add(ErrorCodes.INVALID_SEARCH_INPUT);
            throw new AppException(errors);
        }
    }

    private void setValue(Address address, PreparedStatement statement) {
        try {
            statement.setString(1, address.getStreet());
            statement.setString(2, address.getCity());
            statement.setInt   (3, address.getPincode());
        } catch (SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
    }

    private void constructAddress(ResultSet result, Address address) {
        try {
            address.setId        (result.getLong("id"));
            address.setStreet    (result.getString("street"));
            address.setCity      (result.getString("city"));
            address.setPincode(result.getInt("pincode"));
        } catch (SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
    }

    public Address create(Address address) {

        String insertQuery = QueryManager.CREATE_ADDRESS;
        long generatedId = 0;
        Connection connection = ConnectionManager.getConnection();

        try {
            validate(address);
            PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            setValue(address, statement);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if ((rs.next()) && (rs != null)) {
                generatedId = rs.getLong(1);
            }
            address.setId(generatedId);
        } catch (SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
        return address;
    }

    public Address update(Address address) {

        String query = QueryManager.UPDATE_ADDRESS;
        Connection connection = ConnectionManager.getConnection();

        try {
            validate(address);
            validateId(address.getId(), connection);
            PreparedStatement statement = connection.prepareStatement(query);
            setValue(address, statement);
            statement.setLong  (4, address.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
        return address;
    }

    public Address read(long id) {

        String query = QueryManager.READ_ADDRESS;
        Address address = new Address();
        Connection connection = ConnectionManager.getConnection();

        try {
            validateId(id, connection);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) { constructAddress(result, address); }
            return address;
        } catch(SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
    }

    public ArrayList<Address> readAll() {

        String query = QueryManager.READ_ALL_ADDRESS;
        ArrayList<Address> resultRecords = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Address address = new Address();
                constructAddress(result, address);
                resultRecords.add(address);
            }
        } catch(SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
        return resultRecords;
    }

    public void delete(long id) {

        String query = QueryManager.DELETE_ADDRESS;
        Connection connection = ConnectionManager.getConnection();

        try {
            validateId(id,connection);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new AppException(ErrorCodes.DATABASE_ERROR, e.getCause());
        }
    }

    public List<Address> search(String[] fields, String searchValue) {

        List<Address> result = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection();

        try {
            validateValue(searchValue);
            String query = QueryManager.SEARCH_ADDRESS;
            StringBuilder sb = new StringBuilder(query);

            for (String field : fields) {
                if (field.equals("postalCode")) {
                    field = "postal_code";
                }
                sb.append(String.format("%s LIKE ? OR ", field));
            }
            query = sb.substring(0, sb.length() - 3);

            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 1; i <= fields.length; i++) {
                statement.setString(i, "%" + searchValue + "%");
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Address address = new Address();
                constructAddress(resultSet, address);
                result.add(address);
            }
        } catch (SQLException e) {
            throw new AppException(e.getCause());
        }
        return result;
    }
}