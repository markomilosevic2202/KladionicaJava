package com.marko.kladionicajava.security;


import com.marko.kladionicajava.controller.HomeController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity


public class SecurityConfig {



    public SecurityConfig() {
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {


        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select username, password, active from users where username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select username, role from roles where username=?");


        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                //.antMatchers(HttpMethod.POST).permitAll() // Dozvolite POST metode bez autentifikacije
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll())
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(cofigurer ->
                        cofigurer
                                .accessDeniedPage("/access-denied")

                )
        ;
        return http.build();
    }

}