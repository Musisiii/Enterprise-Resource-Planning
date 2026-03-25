package ERP.app.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_MANAGER,
    ROLE_EMPLOYEE;

    @Override
    public String getAuthority() {
        return name();
    }
}