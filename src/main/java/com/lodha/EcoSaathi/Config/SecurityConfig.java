package com.lodha.EcoSaathi.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CORS enable kiya (Sabse important step)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // 2. CSRF disable kiya
                .csrf(csrf -> csrf.disable())
                
                // 3. API permissions set ki
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/pickup/**").permitAll()
                        .requestMatchers("/api/admin/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/api/issues/**").permitAll()
                        .requestMatchers("/api/bot/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    // Ye naya method joda hai jo bataega ki kon request bhej sakta hai
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // " * " ka matlab hai koi bhi website request bhej sakti hai (Vercel, Localhost sab)
        configuration.setAllowedOrigins(List.of("*")); 
        
        // Konse methods allowed hain
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Headers allow karna
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}