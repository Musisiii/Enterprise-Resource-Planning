package ERP.app.controllers;

import ERP.app.dto.PaySlipRes;
import ERP.app.models.PaySlip;
import ERP.app.services.PayrollService;
import io.swagger.v3.oas.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {
    @Autowired
    private PayrollService service;

    @PostMapping("/generate")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Generate payroll", description = "Generates payroll for a given month and year")
    public ResponseEntity<List<PaySlip>> generatePayroll(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(service.generatePayroll(month, year));
    }

    @PostMapping("/approve")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Approve payroll", description = "Approves payroll for a given month and year")
    public ResponseEntity<?> approvePayroll(@RequestParam int month, @RequestParam int year) {
        service.approvePayroll(month, year);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/month-year")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Get payslips by month/year", description = "Retrieves all payslips for a month and year")
    public ResponseEntity<List<PaySlipRes>> getPaySlipsByMonthYear(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(service.getPaySlipsByMonthYear(month, year));
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_EMPLOYEE')")
    @Operation(summary = "Get payslips by employee", description = "Retrieves payslips for an employee")
    public ResponseEntity<List<PaySlipRes>> getPaySlipsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.getPaySlipsByEmployee(employeeId));
    }
}