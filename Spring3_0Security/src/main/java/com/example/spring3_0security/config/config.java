package com.example.spring3_0security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class config {

    private final UserDetailsService userDetailsService;

    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers("/static/**");
    }

    @Bean
    public BCryptPasswordEncoder BCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http.authorizeHttpRequests()
               .requestMatchers("/login", "/insert", "/user").permitAll()
               .anyRequest().authenticated()
               .and()
               .formLogin()
               .loginPage("/login")
               .usernameParameter("email")
               .passwordParameter("password")
               .defaultSuccessUrl("/loginSuccess")
               .and()
               .logout()
               .logoutSuccessUrl("/logout")
               .invalidateHttpSession(true)
               .and()
               .csrf().disable()
               .build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception{
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }
}
