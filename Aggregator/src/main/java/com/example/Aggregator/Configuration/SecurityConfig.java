package com.example.Aggregator.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

     @Bean
     public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder(11);
     }

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/register", "/verifyToken", "/signin", "/tokenHello", "/preferences/**", "news/**", "/User/**/favorites/**").permitAll()
                         .anyRequest().authenticated()
                 )
                 .formLogin(formlogin -> formlogin.defaultSuccessUrl("/hello", true).permitAll())
                 .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
