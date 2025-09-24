package com.example.goro.config;

import com.example.goro.entiry.Role;
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.goro.service.MyUserDetailsService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired private MyUserDetailsService userDetailsService;
    @Autowired private JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors() // enable CORS
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                // ✅ Public endpoints
                .requestMatchers("/user/register", "/auth/login").permitAll()
                // ✅ Admin-only endpoints
                .requestMatchers("/quiz/createWithQuestions", "/quiz/update/**", "/quiz/delete/**",
                        "/question/add", "/question/update/**", "/question/delete/**")
                .hasAuthority(Role.ADMIN.name())
                // ✅ Students & Admins can view quizzes/questions
                .requestMatchers("/quiz/**", "/question/**")
                .hasAnyAuthority(Role.ADMIN.name(), Role.STUDENT.name())
                // ✅ Allow students/admins to submit attempts
                .requestMatchers("/attempt/**")
                .hasAnyAuthority(Role.ADMIN.name(), Role.STUDENT.name())
                // ✅ Any other requests must be authenticated
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173"); // frontend URL
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
