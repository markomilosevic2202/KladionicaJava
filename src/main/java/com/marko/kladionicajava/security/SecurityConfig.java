package com.marko.kladionicajava.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").hasRole("USER")
                                .requestMatchers("/administrator/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/register/save-user").permitAll()
                                .requestMatchers(HttpMethod.GET,"/register/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll())
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(cofigurer ->
                        cofigurer.accessDeniedPage("/access-denied")
                );
        return http.build();
    }

}