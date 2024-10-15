package com.spring.security.eazybackendapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    /**
     * Configures the default security filter chain.
     * This method sets up the security configuration for the application, enforcing
     * authentication for all requests and enabling form-based login and HTTP Basic authentication.
     *
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/cards","/loans","/balance","/account-details").authenticated()
                .requestMatchers("/notice","/contact").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Configures the user details service.
     * This method sets up the user details service with two users: one with the username
     * "user" and the password "password",
     * and another with the username "admin" and the password "12345".
     *
     * @return the configured UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}EazyBytesUser@123").roles("USER").build();

        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$BoZJnTHruRNJ5P4LfdFeo.NvwIhcBuFRLpEcTHtUVDyv1UVVfcaC2").roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user, admin);
    }


    /**
     * Configures the password encoder.
     * This method sets up the password encoder with a BCrypt password encoder.
     *
     * @return the configured PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Configures the compromised password checker.
     * This method sets up the compromised password checker with the HaveIBeenPwnedRestApiPasswordChecker.
     *
     * @return the configured CompromisedPasswordChecker
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
