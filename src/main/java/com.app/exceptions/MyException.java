package com.app.exceptions;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

public class MyException extends RuntimeException {
    private String exceptionMessage;
    private LocalDateTime exceptionDate;

    public MyException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        this.exceptionDate = LocalDateTime.now();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public LocalDateTime getExceptionDate() {
        return exceptionDate;
    }
}
