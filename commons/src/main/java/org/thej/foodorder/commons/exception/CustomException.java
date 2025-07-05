package org.thej.foodorder.commons.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomException extends RuntimeException {
    private final List<CustomExceptionDetails> errors = new ArrayList<>();

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errors.add(new CustomExceptionDetails(errorCode));
    }

    public void addError(ErrorCode errorCode) {
        this.errors.add(new CustomExceptionDetails(errorCode));
    }

    public void addErrors(List<CustomExceptionDetails> errorDetails) {
        this.errors.addAll(errorDetails);
    }

    public void removeError(ErrorCode errorCode) {
        this.errors.removeIf(error -> error.getErrorCode().equals(errorCode.getCode()));
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

        public CustomExceptionDetails(ErrorCode errorCode){
            this.errorCode = errorCode.getCode();
            this.errorMessage = errorCode.getMessage();
            this.timestamp = java.time.Instant.now().toString();
        }
    }
}
