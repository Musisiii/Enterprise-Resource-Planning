package ERP.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginReq {
    private String email;
    private String password;

}