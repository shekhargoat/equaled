package com.equaled.value;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder @Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class EqualEdException extends RuntimeException{


	private HttpStatus status;
    private String message;
    private String error;
}
