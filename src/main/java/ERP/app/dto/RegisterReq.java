package ERP.app.dto;

import ERP.app.models.EmploymentStatus;
import ERP.app.models.Role;
import ERP.app.models.EmployeeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class RegisterReq {
    // Getters and Setters
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String mobile;
    private LocalDate dateOfBirth;
    private Role roles;
    private EmployeeStatus status;
    private String department;
    private String position;
    private Double baseSalary;
    private EmploymentStatus employmentStatus;
    private LocalDate joiningDate;
}