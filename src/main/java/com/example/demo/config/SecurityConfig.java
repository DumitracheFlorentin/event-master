package com.example.demo.config;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${spring.security.password.encoder}")
    private String passwordEncoderAlgorithm;

    @Value("${spring.security.bcrypt.strength}")
    private int bcryptStrength;

    @Bean
    public PasswordEncoder passwordEncoder() {
        if ("bcrypt".equals(passwordEncoderAlgorithm)) {
            return new BCryptPasswordEncoder(bcryptStrength);
        } else {
            throw new IllegalArgumentException("Unknown password encoder: " + passwordEncoderAlgorithm);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();
    }
}



