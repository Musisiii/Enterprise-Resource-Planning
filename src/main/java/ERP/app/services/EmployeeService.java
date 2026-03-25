package ERP.app.services;

import ERP.app.dto.RegisterReq;
import ERP.app.models.Employee;
import ERP.app.models.Employment;
import ERP.app.models.EmploymentStatus;
import ERP.app.repos.EmployeeRepo;
import ERP.app.repos.EmploymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements UserDetailsService {
    @Autowired
    private EmployeeRepo repository;

    @Autowired
    private EmploymentRepo employmentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Employee registerEmployee(RegisterReq request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setMobile(request.getMobile());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setRoles(request.getRoles());
        employee.setStatus(request.getStatus());

        Employee savedEmployee = repository.save(employee);

        Employment employment = new Employment();
        employment.setEmployee(savedEmployee);
        employment.setDepartment(request.getDepartment());
        employment.setPosition(request.getPosition());
        employment.setBaseSalary(request.getBaseSalary());
        employment.setStatus(request.getEmploymentStatus());
        employment.setJoiningDate(request.getJoiningDate());

        employmentRepo.save(employment);

        return savedEmployee;
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Employee updateEmployee(Long id, RegisterReq request) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());
        existing.setMobile(request.getMobile());
        existing.setDateOfBirth(request.getDateOfBirth());
        existing.setRoles(request.getRoles());
        existing.setStatus(request.getStatus());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Employment employment = existing.getEmployments().stream()
                .filter(emp -> emp.getStatus() == EmploymentStatus.ACTIVE)
                .findFirst()
                .orElse(new Employment());
        employment.setEmployee(existing);
        employment.setDepartment(request.getDepartment());
        employment.setPosition(request.getPosition());
        employment.setBaseSalary(request.getBaseSalary());
        employment.setStatus(request.getEmploymentStatus());
        employment.setJoiningDate(request.getJoiningDate());

        repository.save(existing);
        employmentRepo.save(employment);

        return existing;
    }

    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(),
                employee.getPassword(),
                List.of(employee.getRoles()));
    }
}