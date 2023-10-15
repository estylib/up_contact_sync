package com.up.up_contact_sync.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UPExceptionBuilder {
    public UPException getUpException(ErrorCode errorCode,
                                      HttpStatus httpStatus,
                                      Map<String, Object> params) {
        return UPException.builder()
                .code(errorCode)
                .status(httpStatus)
                .message(errorCode.getMessage())
                .params(params)
                .build();
    }
}
