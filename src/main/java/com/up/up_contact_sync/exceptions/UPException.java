package com.up.up_contact_sync.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UPException extends RuntimeException {

    private ErrorCode code;
    private String message;
    private HttpStatus status;
    @Builder.Default
    private Map<String, Object> params = Map.of();


}
