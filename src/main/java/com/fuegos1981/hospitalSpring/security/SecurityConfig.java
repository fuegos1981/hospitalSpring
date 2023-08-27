package com.fuegos1981.hospitalSpring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    public MyUserDetailsService userDetailsService;
    @Autowired
    public BCryptPasswordEncoder passwordEncoder;
    private static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider()).authorizeHttpRequests().requestMatchers("/hospitalSpring", "/hospitalSpring/error").permitAll()
                .requestMatchers("/hospitalSpring/default","/hospitalSpring/medic","/hospitalSpring/medic/**").hasAnyRole("ADMIN","DOCTOR","NURSE")
                .requestMatchers("/hospitalSpring/schedules/**","/hospitalSpring/appointments/**").fullyAuthenticated()
                .requestMatchers("/hospitalSpring/**").hasAnyRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/hospitalSpring")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/hospitalSpring")
                .defaultSuccessUrl("/hospitalSpring/default")
                .failureUrl("/hospitalSpring/error")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/hospitalSpring?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .csrf()
                .disable();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**", "/styles/**");
    }
}
