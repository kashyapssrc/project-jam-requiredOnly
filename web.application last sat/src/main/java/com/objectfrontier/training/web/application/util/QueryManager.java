package com.objectfrontier.training.web.application.util;

public interface QueryManager {

    static final String CREATE_PERSON = new StringBuilder().append("INSERT INTO person (first_name   ")
                                                          .append("                   , last_name   ")
                                                          .append("                   , email       ")
                                                          .append("                   , birth_date  ")
                                                          .append("                   , is_admin     ")
                                                          .append("VALUES (?, ?, ?, ?, ?)           ")
                                                          .toString();

    static final String UPDATE_PERSON = new StringBuilder().append("UPDATE person          ")
                                                          .append("   SET first_name = ?  ")
                                                          .append("     , last_name = ?   ")
                                                          .append("     , email = ?       ")
                                                          .append("     , birth_date = ?  ")
                                                          .append(" WHERE id = ?          ")
                                                          .toString();

    static final String READ_PERSON = new StringBuilder().append("SELECT id             ")
                                                        .append("     , first_name     ")
                                                        .append("     , last_name      ")
                                                        .append("     , email          ")
                                                        .append("     , birth_date     ")
                                                        .append("     , is_admin     ")
                                                        .append("  FROM person         ")
                                                        .append(" WHERE id = ?         ")
                                                        .toString();

    static final String GET_PERSON_PASSWORD_BY_EMAIL = new StringBuilder().append("SELECT id, email, is_admin             ")
                                                                      .append("  FROM person                         ")
                                                                      .append(" WHERE email = ?                      ")
                                                                      .append("   AND password = ?                   ")
                                                                      .toString();

    static final String READ_ALL_PERSON = new StringBuilder().append("SELECT id             ")
                                                           .append("       , first_name   ")
                                                           .append("       , last_name    ")
                                                           .append("       , email        ")
                                                           .append("       , birth_date   ")
                                                           .append("       , is_admin      ")
                                                           .append("  FROM person         ")
                                                           .toString();

    static final String DELETE_PERSON = new StringBuilder().append("DELETE FROM person ")
                                                          .append("WHERE id = ?       ")
                                                          .toString();

    static final String COUNT_PERSON_BY_NAME = new StringBuilder().append("SELECT COUNT(id) FROM person")
                                                               .append(" WHERE first_name = ?       ")
                                                               .append("   AND last_name = ?        ")
                                                               .toString();

    static final String COUNT_PERSON_BY_EMAIL = new StringBuilder().append("SELECT COUNT(id) FROM person")
                                                                .append(" WHERE email = ?            ")
                                                                .toString();

    static final String CREATE_ADDRESS = new StringBuilder().append("INSERT INTO address (street"    )
                                                           .append("          , city, postal_code) ")
                                                           .append("     VALUES (?, ?, ?)          ")
                                                           .toString();

    static final String UPDATE_ADDRESS = new StringBuilder().append("UPDATE address          ")
                                                           .append("   SET street = ?       ")
                                                           .append("     , city = ?         ")
                                                           .append("     , postal_code = ?  ")
                                                           .append(" WHERE id = ?           ")
                                                           .toString();

    static final String READ_ADDRESS = new StringBuilder().append("SELECT id, street, city, postal_code ")
                                                         .append("FROM address                         ")
                                                         .append("WHERE id = ?                         ")
                                                         .toString();

    static final String READ_ALL_ADDRESS = new StringBuilder().append("SELECT id, street, city, postal_code ")
                                                            .append("FROM address                         ")
                                                            .toString();

    static final String DELETE_ADDRESS = new StringBuilder().append("DELETE FROM address ")
                                                           .append("WHERE id = ?        ")
                                                           .toString();

    static final String SEARCH_ADDRESS = new StringBuffer().append("SELECT id, street, city, postal_code ")
                                                          .append("  FROM address                       ")
                                                          .append(" WHERE                               ")
                                                          .toString();
}