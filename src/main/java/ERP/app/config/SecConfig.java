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
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/api-docs/**").permitAll()
                        // Use hasAuthority because your SQL dump already contains the "ROLE_" prefix
                        .requestMatchers("/api/employees/register").hasAuthority("ROLE_MANAGER")
                        .requestMatchers("/api/employees/**").hasAnyAuthority("ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_ADMIN")
                        .requestMatchers("/api/deductions/**").hasAuthority("ROLE_MANAGER")
                        .requestMatchers("/api/payroll/generate").hasAuthority("ROLE_MANAGER")
                        .requestMatchers("/api/payroll/approve").hasAuthority("ROLE_ADMIN")
                        // Ensure ADMIN can view payroll list to approve them
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