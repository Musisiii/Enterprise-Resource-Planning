package ERP.app.services;

import ERP.app.dto.RegisterReq;
import ERP.app.models.Employee;
import ERP.app.repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    @Autowired
    private EmployeeRepo repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Employee registerUser(RegisterReq request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setMobile(request.getMobile());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setRoles(request.getRoles());
        employee.setStatus(request.getStatus());

        return repository.save(employee);
    }
}