package ERP.app.controllers;

import ERP.app.dto.RegisterReq;
import ERP.app.models.Employee;
import ERP.app.services.EmployeeService;
import io.swagger.v3.oas.annotations.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Create employee", description = "Creates a new employee with employment details")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody RegisterReq request) {
        return ResponseEntity.ok(service.registerEmployee(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Get all employees", description = "Retrieves all employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_EMPLOYEE')")
    @Operation(summary = "Get employee by ID", description = "Retrieves employee details")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return service.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Update employee", description = "Updates employee details")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody RegisterReq request) {
        return ResponseEntity.ok(service.updateEmployee(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Delete employee", description = "Deletes an employee")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}