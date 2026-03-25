package ERP.app.controllers;

import ERP.app.models.Deduction;
import ERP.app.services.DeductionService;
import io.swagger.v3.oas.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deductions")
public class DeductionController {
    @Autowired
    private DeductionService service;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Create deduction", description = "Creates a new deduction")
    public ResponseEntity<Deduction> createDeduction(@RequestBody Deduction deduction) {
        return ResponseEntity.ok(service.createDeduction(deduction));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Get all deductions", description = "Retrieves all deductions")
    public ResponseEntity<List<Deduction>> getAllDeductions() {
        return ResponseEntity.ok(service.getAllDeductions());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Get deduction by ID", description = "Retrieves deduction details")
    public ResponseEntity<Deduction> getDeductionById(@PathVariable Long id) {
        return service.getDeductionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Update deduction", description = "Updates deduction details")
    public ResponseEntity<Deduction> updateDeduction(@PathVariable Long id, @RequestBody Deduction deduction) {
        return ResponseEntity.ok(service.updateDeduction(id, deduction));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @Operation(summary = "Delete deduction", description = "Deletes a deduction")
    public ResponseEntity<?> deleteDeduction(@PathVariable Long id) {
        service.deleteDeduction(id);
        return ResponseEntity.ok().build();
    }
}