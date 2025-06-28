package org.thej.foodorder.webcommons.dto.template.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private String timestamp;
    private T data;

    public ApiResponse() {
        this.status = HttpStatus.OK;
        this.message = "Operation completed successfully";
        this.timestamp = getCurrentTimestamp();
    }

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = getCurrentTimestamp();
    }

    private String getCurrentTimestamp() {
        return java.time.Instant.now().toString();
    }
}
