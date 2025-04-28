package com.meli.challenge.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class FieldValidatorException extends ChallengeException {

    private static final long serialVersionUID = 206974396811100437L;


    public FieldValidatorException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    @Override
    public String getSysCode() {
        return null;
    }

}
