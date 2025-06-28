package org.thej.foodorder.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.thej.foodorder.webcommons.dto.request.BaseRequest;

@EqualsAndHashCode(callSuper = false)
@Data
public class LoginRequest extends BaseRequest {
    private String username;
    private String password;
}
