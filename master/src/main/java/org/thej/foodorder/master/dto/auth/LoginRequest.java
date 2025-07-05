package org.thej.foodorder.master.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.thej.foodorder.master.dto.template.request.BaseRequest;

@EqualsAndHashCode(callSuper = false)
@Data
public class LoginRequest extends BaseRequest {
    private String username;
    private String password;
}
