package ERP.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "employment")
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Position is required")
    private String position;

    @Positive(message = "Base salary must be positive")
    private double baseSalary;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private EmploymentStatus status;

    @PastOrPresent(message = "Joining date cannot be in the future")
    private LocalDate joiningDate;

    public Employment() {}

}