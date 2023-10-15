package com.up.up_contact_sync.exceptions;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UPExceptionSchema {
    private String code;
    private String message;
    @Builder.Default
    private Map<String, Object> params = Map.of();

}
