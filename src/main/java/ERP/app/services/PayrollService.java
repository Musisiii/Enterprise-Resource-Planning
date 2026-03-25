package ERP.app.services;

import ERP.app.dto.PaySlipRes;
import ERP.app.models.*;
import ERP.app.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollService {
    private static final Logger logger = LoggerFactory.getLogger(PayrollService.class);

    @Autowired
    private EmployeeRepo employeeRepository;

    @Autowired
    private EmploymentRepo employmentRepository;

    @Autowired
    private DeductionRepo deductionRepository;

    @Autowired
    private PaySlipRepo paySlipRepository;

    @Autowired
    private MessageRepo messageRepository;

    @Autowired
    private Email emailService;

    @Transactional
    public List<PaySlip> generatePayroll(int month, int year) {
        logger.info("Generating payroll for {}/{}", month, year);

        List<Employment> activeEmployments = employmentRepository.findByStatus(EmploymentStatus.ACTIVE);
        if (activeEmployments.isEmpty()) {
            logger.warn("No active employments found");
            throw new IllegalStateException("No active employments found");
        }

        List<Deduction> deductions = deductionRepository.findAll();
        if (deductions.isEmpty()) {
            logger.error("No deductions found in database");
            throw new IllegalStateException("Deduction records are missing");
        }

        double employeeTaxRate = getDeductionRate(deductions, "employee tax", 30.0);
        double pensionRate = getDeductionRate(deductions, "pension", 6.0); // Updated to 6%
        double medicalRate = getDeductionRate(deductions, "medical insurance", 5.0);
        double otherRate = getDeductionRate(deductions, "others", 5.0);
        double houseRate = getDeductionRate(deductions, "housing", 14.0); // Updated to 14%
        double transportRate = getDeductionRate(deductions, "transport", 14.0); // Updated to 14%

        List<PaySlip> paySlips = activeEmployments.stream().map(employment -> {
            Employee employee = employment.getEmployee();
            if (paySlipRepository.findByEmployeeAndMonthAndYear(employee, month, year).isPresent()) {
                logger.warn("Payroll already generated for employee {} for {}/{}", employee.getEmail(), month, year);
                throw new IllegalStateException("Payroll already generated for employee " + employee.getEmail());
            }

            double baseSalary = employment.getBaseSalary();
            double house = baseSalary * houseRate;
            double transport = baseSalary * transportRate;
            double grossSalary = baseSalary + house + transport;

            double employeeTax = grossSalary * employeeTaxRate;
            double pension = grossSalary * pensionRate;
            double medical = grossSalary * medicalRate;
            double other = grossSalary * otherRate;
            double totalDeductions = employeeTax + pension + medical + other;

            if (totalDeductions >= grossSalary) {
                logger.error("Deductions {} exceed gross salary {} for employee {}", totalDeductions, grossSalary, employee.getEmail());
                throw new IllegalStateException("Deductions exceed gross salary for employee " + employee.getEmail());
            }

            double netSalary = Math.round((grossSalary - totalDeductions) * 100.0) / 100.0;

            PaySlip paySlip = new PaySlip();
            paySlip.setEmployee(employee);
            paySlip.setHouse(house);
            paySlip.setTransport(transport);
            paySlip.setEmployeeTaxedAmount(employeeTax);
            paySlip.setPension(pension);
            paySlip.setMedicalInsurance(medical);
            paySlip.setOtherTaxedAmount(other);
            paySlip.setGrossSalary(grossSalary);
            paySlip.setNetSalary(netSalary);
            paySlip.setMonth(month);
            paySlip.setYear(year);
            paySlip.setStatus(PaySlipStatus.PENDING);

            logger.info("Generated payslip for employee {}: netSalary={}", employee.getEmail(), netSalary);
            return paySlipRepository.save(paySlip);
        }).collect(Collectors.toList());

        return paySlips;
    }

    @Transactional
    public void approvePayroll(int month, int year) {
        logger.info("Approving payroll for {}/{}", month, year);

        List<PaySlip> paySlips = paySlipRepository.findByMonthAndYear(month, year);
        if (paySlips.isEmpty()) {
            logger.warn("No payslips found for {}/{}", month, year);
            throw new IllegalStateException("No payslips found for approval");
        }

        paySlips.forEach(paySlip -> {
            if (paySlip.getStatus() == PaySlipStatus.PENDING) {
                paySlip.setStatus(PaySlipStatus.PAID);
                paySlipRepository.save(paySlip);
                logger.info("Approved payslip ID {} for employee {}", paySlip.getId(), paySlip.getEmployee().getEmail());
            }
        });

        List<Message> messages = messageRepository.findByMonthAndYear(month, year);
        if (messages.isEmpty()) {
            logger.warn("No messages found for {}/{}", month, year);
        }

        messages.forEach(message -> {
            try {
                emailService.sendEmail(
                        message.getEmployee().getEmail(),
                        "Salary Payment Notification",
                        message.getMessage()
                );
                logger.info("Sent email to {}", message.getEmployee().getEmail());
            } catch (Exception e) {
                logger.error("Failed to send email to {}: {}", message.getEmployee().getEmail(), e.getMessage());
            }
        });
    }

    public List<PaySlipRes> getPaySlipsByMonthYear(int month, int year) {
        return paySlipRepository.findByMonthAndYear(month, year).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<PaySlipRes> getPaySlipsByEmployee(Long employeeId) {
        return paySlipRepository.findByEmployeeId(employeeId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private double getDeductionRate(List<Deduction> deductions, String name, double defaultRate) {
        return deductions.stream()
                .filter(d -> d.getDeductionName().equals(name))
                .findFirst()
                .map(Deduction::getPercentage)
                .map(rate -> rate / 100)
                .orElse(defaultRate / 100);
    }

    private PaySlipRes toResponse(PaySlip paySlip) {
        PaySlipRes response = new PaySlipRes();
        response.setId(paySlip.getId());
        response.setEmployeeId(paySlip.getEmployee().getId());
        response.setEmployeeName(paySlip.getEmployee().getFirstName() + " " + paySlip.getEmployee().getLastName());
        response.setHouse(paySlip.getHouse());
        response.setTransport(paySlip.getTransport());
        response.setEmployeeTaxedAmount(paySlip.getEmployeeTaxedAmount());
        response.setPension(paySlip.getPension());
        response.setMedicalInsurance(paySlip.getMedicalInsurance());
        response.setOtherTaxedAmount(paySlip.getOtherTaxedAmount());
        response.setGrossSalary(paySlip.getGrossSalary());
        response.setNetSalary(paySlip.getNetSalary());
        response.setMonth(paySlip.getMonth());
        response.setYear(paySlip.getYear());
        response.setStatus(paySlip.getStatus().name());
        return response;
    }
}