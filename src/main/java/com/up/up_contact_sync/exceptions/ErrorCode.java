package com.up.up_contact_sync.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UP_UNAUTHORIZED("Invalid username or password"),
    UP_TOO_MANY_ATTEMPTS("Too many incorrect requests"),
    UP_NO_VALUE_PRESENT("No value present"),
    UP_INVALID_DATA("invalid data"),
    UP_LIMIT_REACHED("limit droplets reached"),
    UP_INVALID_CONTACT_TOKEN("Contacts token invalid or expired");


    private final String message;


}
