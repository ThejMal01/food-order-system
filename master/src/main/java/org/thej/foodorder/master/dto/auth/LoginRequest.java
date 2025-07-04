package org.thej.foodorder.webcommons.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.thej.foodorder.webcommons.dto.template.request.BaseRequest;

@EqualsAndHashCode(callSuper = false)
@Data
public class LoginRequest extends BaseRequest {
    private String username;
    private String password;
}
