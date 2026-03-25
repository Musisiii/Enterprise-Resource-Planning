package ERP.app.dto;

import ERP.app.models.EmploymentStatus;
import ERP.app.models.Role;

import java.time.LocalDate;
import java.util.Set;

public class EmployeeRes {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
    private String department;
    private String position;
    private Double baseSalary;
    private EmploymentStatus status;
    private LocalDate joiningDate;
}