package ERP.app.controllers;

import ERP.app.models.Message;
import ERP.app.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest request) {
        Message message = messageService.sendMessage(
                request.getEmployeeId(),
                request.getMessage(),
                request.getMonth(),
                request.getYear()
        );
        return ResponseEntity.ok(message);
    }

    static class MessageRequest {
        private Long employeeId;
        private String message;
        private int month;
        private int year;

        public Long getEmployeeId() { return employeeId; }
        public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public int getMonth() { return month; }
        public void setMonth(int month) { this.month = month; }
        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }
    }
}