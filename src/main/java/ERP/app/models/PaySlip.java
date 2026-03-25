package ERP.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "pay_slip", uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "month", "year"}))
public class PaySlip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @PositiveOrZero(message = "House allowance must be non-negative")
    private double house;

    @PositiveOrZero(message = "Transport allowance must be non-negative")
    private double transport;

    @PositiveOrZero(message = "EmployeeController tax must be non-negative")
    private double employeeTaxedAmount;

    @PositiveOrZero(message = "Pension must be non-negative")
    private double pension;

    @PositiveOrZero(message = "Medical insurance must be non-negative")
    private double medicalInsurance;

    @PositiveOrZero(message = "Other taxes must be non-negative")
    private double otherTaxedAmount;

    @Positive(message = "Gross salary must be positive")
    private double grossSalary;

    @PositiveOrZero(message = "Net salary must be non-negative")
    private double netSalary;

    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private int month;

    @Min(value = 2000, message = "Year must be valid")
    private int year;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private PaySlipStatus status;

    public PaySlip() {}

}