package com.objectfrontier.training.web.application.util;

public enum ErrorCodes {

    INVALID_ADDRESS_ID   (101, "ID cannot be empty"                             ),
    INVALID_STREET       (102, "Street cannot be null"                          ),
    INVALID_CITY         (103, "City cannot be null"                            ),
    INVALID_PINCODE      (104, "Pincode cannot be empty"                        ),
    INVALID_PERSON_ID    (101, "ID cannot be empty"                             ),
    INVALID_FIRST_NAME   (106, "First Name cannot be empty"                     ),
    INVALID_LAST_NAME    (107, "Last Name cannot be empty"                      ),
    INVALID_EMAIL        (108, "Email cannot be empty"                          ),
    INVALID_BIRTH_DATE   (109, "Birth Date cannot be empty"                     ),
    INVALID_NAME         (110, "First name and Last Name cannot be same"        ),
    DUPLICATE_EMAIL      (111, "Email already exists"                           ),
    DATABASE_ERROR       (112, "Error in connection"                            ),
    INVALID_SEARCH_INPUT (113, "Search input cannot be null"                    ),
    UNKNOWN_ERROR        (114, "unknown error"                                  ),
    LOAD_JSON_ERROR      (115, "Error in loading Json data"                     ),
    STORE_JSON_ERROR     (116, "Error in storing Json data"                     ),
    INVALID_URL_EXCEPTION(117, "Invalid URL"                                    ),
    PARSER_EXCEPTION     (118, "Exception in parsing"                           ),
    UNAUTHENTICATED_USER (401, "Password is invalid"                            ),
    INVALID_CREDENTIALS  (120, "Email and password cannot be null"              ),
    UNAUTHORIZED_USER    (403, "Unauthorized for requested method"              ),
    INVALID_REQUEST      (122, "Error either in request URL or requested method"),
    INTERNAL_SERVER_ERROR(500, "Unable to connect due to server side error"     ),
    CONNECTION_ERROR     (404, "unable to connect to data resource"             );

    int errorCode;
    String errorMessage;

    ErrorCodes(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
};
