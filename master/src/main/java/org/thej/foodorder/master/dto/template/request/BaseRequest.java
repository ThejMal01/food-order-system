package org.thej.foodorder.master.dto.template.request;

import lombok.Data;

@Data
public class BaseRequest {
    private String requestId;
    private String timestamp;
    public BaseRequest() {
        this.requestId = generateRequestId();
        this.timestamp = getCurrentTimestamp();
    }

    private String generateRequestId() {
        return java.util.UUID.randomUUID().toString();
    }

    private String getCurrentTimestamp() {
        return java.time.Instant.now().toString();
    }

}