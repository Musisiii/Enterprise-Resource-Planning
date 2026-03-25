package ERP.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaySlipRes {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private double house;
    private double transport;
    private double employeeTaxedAmount;
    private double pension;
    private double medicalInsurance;
    private double otherTaxedAmount;
    private double grossSalary;
    private double netSalary;
    private int month;
    private int year;
    private String status;

}