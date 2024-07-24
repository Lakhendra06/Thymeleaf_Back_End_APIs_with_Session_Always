/*
 * package com.tms.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.tms.filter.AuthoritiesLoggingAfterFilter;
import com.tms.filter.AuthoritiesLoggingAtFilter;
import com.tms.filter.CsrfCookieFilter;
import com.tms.filter.JWTTokenGeneratorFilter;
import com.tms.filter.JWTTokenValidatorFilter;
import com.tms.filter.RequestValidationBeforeFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
//@EnableMethodSecurity
//@EnableWebSecurity
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        //String[] str = {"Authorization"};

        http.securityContext( (context) -> context.requireExplicitSave(false))
        //http
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
        .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request){
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                //config.setExposedHeaders(Arrays.asList(str));
                config.setMaxAge(3600L);
                return config;
            }
        })).csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/register","/update_register")
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
        .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
        .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
        .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
        .authorizeHttpRequests((requests) -> requests

            .requestMatchers("/").hasAnyRole("SUPERADMIN","ADMIN")

            .requestMatchers("/logout").hasAnyRole("EMPLOYEE","HOD","ADMIN","SUPERADMIN")

            .requestMatchers("/employees/**").hasAnyRole("EMPLOYEE","HOD","ADMIN","SUPERADMIN")

            .requestMatchers("/departments/**").hasAnyRole("EMPLOYEE","HOD","ADMIN","SUPERADMIN")

            .requestMatchers("/departments/**").hasAnyRole("EMPLOYEE","HOD","ADMIN","SUPERADMIN")

            .requestMatchers("/roles/**").hasAnyRole("EMPLOYEE","HOD","ADMIN","SUPERADMIN")

            //.requestMatchers("/").authenticated()

            .requestMatchers(
                "/login",
                "/getUserName",
                "/under-maintenance",
                "/js/**",
                "/assets/**",
                "/template_js/**",
                "/dist/**",
                "/getCurrentDate").permitAll());
        
        http.formLogin().loginPage("/login");
        http.httpBasic(Customizer.withDefaults());
        
        return http.build();
    }
    
    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
 */