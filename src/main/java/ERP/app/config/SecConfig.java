package ERP.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    // 1. PUBLIC: Only login and documentation
                    .requestMatchers("/api/auth/login", "/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**").permitAll()

                    // 2. REGISTRATION: Secure the auth/register endpoint (Move out of permitAll)
                    // Match the pattern in AuthController
                    .requestMatchers("/api/auth/register").hasAuthority("ROLE_MANAGER")
                        
                    // 3. EMPLOYEES: Match the POST in EmployeeController
                    // Managers add employees, Employees view their own (logic in Service)
                    .requestMatchers(HttpMethod.POST, "/api/employees").hasAuthority("ROLE_MANAGER")
                    .requestMatchers("/api/employees/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_ADMIN")

                    // 4. DEDUCTIONS: Manager only
                    .requestMatchers("/api/deductions/**").hasAuthority("ROLE_MANAGER")

                    // 5. PAYROLL: Specific actions for Manager and Admin
                    .requestMatchers("/api/payroll/generate").hasAuthority("ROLE_MANAGER")
                    .requestMatchers("/api/payroll/approve").hasAuthority("ROLE_ADMIN")
                        
                    // Allow everyone to see payroll (Employee sees own, Admin/Manager see all)
                    .requestMatchers("/api/payroll/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_ADMIN")

                    .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}