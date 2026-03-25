package ERP.app.repos;

import ERP.app.models.Deduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeductionRepo extends JpaRepository<Deduction, Long> {
    Optional<Deduction> findByDeductionName(String deductionName);
}