package ERP.app.services;

import ERP.app.models.Deduction;
import ERP.app.repos.DeductionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeductionService {
    @Autowired
    private DeductionRepo repository;

    public Deduction createDeduction(Deduction deduction) {
        return repository.save(deduction);
    }

    public List<Deduction> getAllDeductions() {
        return repository.findAll();
    }

    public Optional<Deduction> getDeductionById(Long id) {
        return repository.findById(id);
    }

    public Deduction updateDeduction(Long id, Deduction deduction) {
        Deduction existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deduction not found"));
        existing.setDeductionName(deduction.getDeductionName());
        existing.setPercentage(deduction.getPercentage());
        return repository.save(existing);
    }

    public void deleteDeduction(Long id) {
        repository.deleteById(id);
    }

    public Optional<Deduction> findByDeductionName(String name) {
        return repository.findByDeductionName(name);
    }
}