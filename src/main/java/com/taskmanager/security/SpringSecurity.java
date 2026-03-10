package com.taskmanager.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableMethodSecurity
@Configuration
public class SpringSecurity {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtFilter jwtFilter;
    
    public SpringSecurity(UserDetailsServiceImpl userDetailsServiceImpl, JwtFilter jwtFilter) {
    	this.userDetailsService = userDetailsServiceImpl;
    	this.jwtFilter = jwtFilter;
    }

    // ========== PASSWORD ENCODER ==========
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // ========== AUTHENTICATION MANAGER ==========
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ========== SECURITY FILTER CHAIN ==========
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            		
            		// Frontend files allow
            		.requestMatchers(
            				"/",
            		        "/login.html",
            		        "/register.html",

            		        "/admin/**",
            		        "/user/**",

            		        "/css/**",
            		        "/js/**",
            		        "/images/**",
            		        "/favicon.*"
                    ).permitAll()
            		
            		// Public APIs
                .requestMatchers("/api/v1/auth/**").permitAll()
                
                // Admin APIs
                .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                
                // User APIs
                .requestMatchers("/api/v1/tasks/**").hasAuthority("USER")
                
                .anyRequest().authenticated()
            )
            .userDetailsService(userDetailsService)
            .addFilterBefore(jwtFilter, 
            			UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
