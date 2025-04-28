package com.meli.challenge.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ChallengeException extends RuntimeException {

    private static final long serialVersionUID = -8017748944528900246L;

    private HttpStatus httpStatus;

    protected String errorCode;

    private Object[] paramenters;

    public abstract String getSysCode();


    public ChallengeException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ChallengeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ChallengeException(String error) {
        super(error);
    }
}

