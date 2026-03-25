package ERP.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "deductions")
public class Deduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "DeductionRepo name is required")
    private String deductionName;

    @PositiveOrZero(message = "Percentage must be non-negative")
    @Max(value = 100, message = "Percentage cannot exceed 100")
    private double percentage;

    public Deduction() {}

}