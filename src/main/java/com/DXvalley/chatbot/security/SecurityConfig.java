package com.DXvalley.chatbot.security;
import com.DXvalley.chatbot.security.CustomUserDeatailsService;
import com.DXvalley.chatbot.security.filters.JwtAuthenticationFilter;
import com.DXvalley.chatbot.security.filters.JwtAuthorizationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import static org.springframework.http.HttpMethod.*;


import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor

public class SecurityConfig{
    private final CustomUserDeatailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/**").permitAll();
//          auth.requestMatchers("/manageAdmins/**").hasAuthority("System Admin");
                })
                .addFilter(new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration)))
                .addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}