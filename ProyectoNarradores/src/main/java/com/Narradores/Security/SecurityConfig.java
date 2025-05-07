package com.Narradores.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

//Clase de configuracion
@Configuration
//Habilitar seguridad
@EnableWebSecurity
//Habilitar método para la clave
@EnableMethodSecurity
public class SecurityConfig {
	//para encenciptar contraseñas
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	/* Se utiliza para cargar los detalles de usuario (nombre de usuario, contraseña, roles)
	 *  desde la base de datos o cualquier otra fuente de datos.*/
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserSecurity();

	}

	
	/*Configura la cadena de filtros de seguridad de Spring, estableciendo qué rutas son accesibles públicamente (permitAll) y 
	 * cuáles requieren autenticación (authenticated). 
	 * También define la página de inicio de sesión personalizada y la URL de éxito posterior al inicio de sesión.*/
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable()) // Desactivar CSRF (solo si es necesario)
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(
	                "/usuario/registroUsu", "/usuario/**", "/static/**", "/resources/img/**", 
	                "/resources/css/**", "/imagenes/**", "/paginaPrincipal/**", "/producto/**",
	                "/narradores/**","/error/**" // Permitir registro vía AJAX
	            ).permitAll() // Permitir acceso sin autenticación
	            .anyRequest().authenticated() // Requiere autenticación para otras rutas
	        )
	        .formLogin(form -> form
	                .loginPage("/usuario/login")
	                .loginProcessingUrl("/usuario/login")  // Añadir esta línea
	                .defaultSuccessUrl("/usuario/principal", true)
	                .failureUrl("/usuario/login?error=true")  // Añadir esta línea
	                .permitAll()
	            )
	            .logout(logout -> logout
	                .logoutSuccessUrl("/usuario/login?logout=true")  // Añadir esta línea
	                .permitAll()
	            );

	    return http.build();
	}
	
	/*Funcionalidad: Proporciona un AuthenticationProvider que usa DaoAuthenticationProvider, configurado con el UserDetailsService 
	 * y el PasswordEncoder (BCrypt en este caso).
	 *  Es el encargado de autenticar a los usuarios.*/

	@Bean
	  public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
	
	@Bean
	public HttpFirewall allowSemicolonFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowSemicolon(true);  // Permitir ";" en las URLs
	    return firewall;
	}

	
}