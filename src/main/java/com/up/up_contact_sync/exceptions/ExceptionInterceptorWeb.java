package com.up.up_contact_sync.exceptions;


import com.up.up_contact_sync.service.utils.UPLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class
ExceptionInterceptorWeb {

    @ExceptionHandler(UPException.class)
    public final ResponseEntity<Object> handleUPExceptions(UPException ex) {
        var exceptionResponse = UPExceptionSchema
                .builder()
                .message(ex.getMessage())
                .params(ex.getParams())
                .code(ex.getCode().toString())
                .build();
        UPLogger.log().error(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(exceptionResponse, ex.getStatus());
    }


}
