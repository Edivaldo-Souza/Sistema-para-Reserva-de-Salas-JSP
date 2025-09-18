package com.dunnas.reservasalas.config;

import com.dunnas.reservasalas.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(crsf -> crsf.disable())
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/css/**","/WEB-INF/jsp/**","/js/**", "/images/**").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/rooms").hasRole("ADMINISTRADOR")
                                .requestMatchers("/home").hasAnyRole(
                                        UserRole.ADMINISTRADOR.name(),
                                        UserRole.RECEPCIONISTA.name(),
                                        UserRole.CLIENTE.name())
                                .anyRequest().authenticated())
                .formLogin(
                        formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/exec_login")
                        .defaultSuccessUrl("/home",true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(
                        logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .clearAuthentication(true));

        return http.build();
    }
}
