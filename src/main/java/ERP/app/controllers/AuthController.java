package ERP.app.controllers;

import ERP.app.dto.LoginReq;
import ERP.app.dto.RegisterReq;
import ERP.app.models.Employee;
import ERP.app.services.AuthService;
import ERP.app.utils.JwtUtil;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticates user and returns JWT token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<?> login(@RequestBody LoginReq request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    @Operation(summary = "Register employee", description = "Registers a new employee (ROLE_MANAGER only)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "EmployeeController registered"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<?> register(@RequestBody RegisterReq request) {
        Employee employee = this.service.registerUser(request);
        return ResponseEntity.ok(employee);
    }
}