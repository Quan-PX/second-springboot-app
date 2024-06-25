package com.quanpham.secondApp.Exception;

public enum ErrorCode {
    USER_EXISTED("User existed",1001),
    INVALID_MESSAGE_KEY("Invalid message key", 6969),
    UNCATEGORIZED_EXCEPTION("Uncategorized exception",9999),
    USERNAME_INVALID("Username must be at least 8 character", 1002),
    PASSWORD_INVALID("Username must be at least 8 character", 1003),
    USER_NOT_EXISTED("User not existed", 1005),
    UNAUTHENTICATED("Unauthenticated", 6666)
    ;

    ErrorCode(String message, int code) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
