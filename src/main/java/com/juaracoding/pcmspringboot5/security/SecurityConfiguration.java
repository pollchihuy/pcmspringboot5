package com.juaracoding.pcmspringboot5.security;


import com.juaracoding.pcmspringboot5.service.AppDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    @Qualifier("customAuthenticationEntryPoint")
    private AuthenticationEntryPoint authenticationEntryPoint;
    // AuthenticationEntryPoint authenticationEntryPoint = new JwtAuthenticationEntryPoint();

    @Autowired
    private AppDetailServices authService;

    /*
        401 -> Otentikasi
        403 -> Forbiden / Otorisasi
     */

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authService);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtFilter filter) throws Exception {
        http.
            csrf(AbstractHttpConfigurer::disable).
            authorizeHttpRequests(
                    request->request.requestMatchers(
                            "/auth/**",
                            "/swagger-ui/**",
                            "/h2-console/**",
                            "/v3/api-docs/**"
                    ).permitAll().anyRequest().authenticated()).
//            headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())). // Allow H2 console to run in a frame
            httpBasic(basic -> basic.authenticationEntryPoint(authenticationEntryPoint)).
            exceptionHandling(Customizer.withDefaults()).
            sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
            authenticationProvider(authenticationProvider()).
                addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}