package com.equaled.customexception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Setter @Getter
@AllArgsConstructor
@Builder
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 938213931489215850L;
    private HttpStatus status;
    private String message;
    private String error;
}

