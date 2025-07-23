package org.thej.foodorder.commons.exception;

public enum ErrorCode {
    // Common errors
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    FORBIDDEN("403", "Forbidden"),
    NOT_FOUND("404", "Not Found"),

    // User-related errors
    USER_NOT_FOUND("1001", "User Not Found"),
    USERNAME_ALREADY_EXISTS("1002", "Username Already Exists"),
    INVALID_CREDENTIALS("1003", "Invalid Credentials"),
    ROLE_NOT_FOUND("1004", "User Role Not Found"),

    // Order-related errors
    ORDER_NOT_FOUND("2001", "Order Not Found"),
    ORDER_ALREADY_EXISTS("2002", "Order Already Exists"),;


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
