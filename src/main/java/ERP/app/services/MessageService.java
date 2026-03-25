package ERP.app.services;

import ERP.app.models.Employee;
import ERP.app.models.Message;
import ERP.app.models.PaySlip;
import ERP.app.repos.EmployeeRepo;
import ERP.app.repos.MessageRepo;
import ERP.app.repos.PaySlipRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private static final String DEFAULT_INSTITUTION = "ERP Corp";
    private static final String DEFAULT_MESSAGE_TEMPLATE = 
        "Dear %s, Your salary for %d/%d from %s amounting to %.2f " +
        "has been successfully credited to your account (%d) successfully.";

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PaySlipRepo paySlipRepo;

    @Autowired
    private Email emailService;

    @Transactional
    public Message sendMessage(Long employeeId, String messageContent, int month, int year) {
        logger.info("Creating message for employeeId: {}, month: {}, year: {}", employeeId, month, year);

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (year < 2000 || year > 9999) {
            throw new IllegalArgumentException("Year must be between 2000 and 9999");
        }

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        String finalMessage = messageContent;
        if (finalMessage == null || finalMessage.isBlank()) {
            PaySlip paySlip = paySlipRepo.findByEmployeeIdAndMonthAndYear(employeeId, month, year)
                    .orElseThrow(() -> new IllegalArgumentException("Payslip not found for employeeId: " + employeeId + " for " + month + "/" + year));
            finalMessage = String.format(
                    DEFAULT_MESSAGE_TEMPLATE,
                    employee.getFirstName(),
                    month,
                    year,
                    DEFAULT_INSTITUTION,
                    paySlip.getNetSalary(),
                    employeeId
            );
        }

        Message message = new Message();
        message.setEmployee(employee);
        message.setMessage(finalMessage);
        message.setMonth(month);
        message.setYear(year);
        message.setSentAt(java.time.LocalDateTime.now());

        Message savedMessage = messageRepo.save(message);

        try {
            emailService.sendEmail(
                    employee.getEmail(),
                    "Salary Credit Notification",
                    finalMessage
            );
            logger.info("Email sent to {}", employee.getEmail());
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", employee.getEmail(), e.getMessage());
        }

        return savedMessage;
    }
}