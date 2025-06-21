package org.thej.foodorder.commons.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomException extends RuntimeException {
    private final List<CustomExceptionDetails> errors = new ArrayList<>();

    public CustomException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errors.add(new CustomExceptionDetails(errorCode, errorMessage));
    }

    public void addError(String errorCode, String errorMessage) {
        this.errors.add(new CustomExceptionDetails(errorCode, errorMessage));
    }

    public void addErrors(List<CustomExceptionDetails> errorDetails) {
        this.errors.addAll(errorDetails);
    }

    @Getter
    public static class CustomExceptionDetails {
        private final String errorCode;
        private final String errorMessage;
        private final String timestamp;

        public CustomExceptionDetails(String errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            this.timestamp = java.time.Instant.now().toString();
        }
    }
}
