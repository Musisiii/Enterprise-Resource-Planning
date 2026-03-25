package ERP.app.repos;

import ERP.app.models.Employment;
import ERP.app.models.EmploymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploymentRepo extends JpaRepository<Employment, Long> {
    List<Employment> findByStatus(EmploymentStatus status);
}