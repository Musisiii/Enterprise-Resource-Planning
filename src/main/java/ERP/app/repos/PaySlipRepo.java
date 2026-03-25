package ERP.app.repos;

import ERP.app.models.Employee;
import ERP.app.models.PaySlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaySlipRepo extends JpaRepository<PaySlip, Long> {
    Optional<PaySlip> findByEmployeeAndMonthAndYear(Employee employee, int month, int year);
    List<PaySlip> findByMonthAndYear(int month, int year);
    List<PaySlip> findByEmployeeId(Long employeeId);
    Optional<PaySlip> findByEmployeeIdAndMonthAndYear(Long employeeId, int month, int year);
}