package com.quanpham.secondApp.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED("User existed",1001, HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_MESSAGE_KEY("Invalid message key", 6969, HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION("Uncategorized exception",9999, HttpStatus.BAD_REQUEST),
    USERNAME_INVALID("Username must be at least 8 character", 1002, HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID("Username must be at least 8 character", 1003, HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("User not existed", 1005, HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("Unauthenticated", 6666, HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("UnAuthorized - you do not permission", 103, HttpStatus.FORBIDDEN),

    ;

    ErrorCode(String message, int code, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

}
