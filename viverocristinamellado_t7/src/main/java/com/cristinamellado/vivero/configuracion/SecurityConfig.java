package com.cristinamellado.vivero.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Deshabilitamos CSRF para simplificar
                .authorizeHttpRequests(auth -> auth
                                // Permitir acceso a recursos estáticos
                                .requestMatchers("/css/**", "/img/**").permitAll()
                                // Permitir acceso a páginas de autenticación
                                .requestMatchers("/auth/**", "/clientes/registrar-cliente").permitAll()
                                // Rutas privadas
                                .requestMatchers("/personas/**", "/plantas/**").hasRole("ADMINISTRADOR")
                                .requestMatchers("/mensajes/**").hasAnyRole("ADMINISTRADOR", "PERSONAL")
                                .requestMatchers("/ejemplares/**").hasAnyRole("ADMINISTRADOR", "PERSONAL")
                                // Cualquier otra ruta es privada
                                .anyRequest().permitAll() //De momento publica para pruebas, Cambiar despues a authenticated().
                )
                .formLogin(form -> form
                                .loginPage("/inicio")
                                .loginProcessingUrl("/auth/login")
                                .defaultSuccessUrl("/auth/redireccionar")
                                .failureUrl("/auth/error-login")
                                .permitAll()
                )
                .logout(logout -> logout
                                .logoutUrl("/auth/logout")
                                .logoutSuccessUrl("/inicio")
                                .permitAll()
                )
                .exceptionHandling(exception -> exception
                                .accessDeniedPage("/auth/acceso-denegado")
                );
    
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
