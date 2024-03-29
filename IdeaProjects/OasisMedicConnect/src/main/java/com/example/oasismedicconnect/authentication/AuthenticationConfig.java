package com.example.oasismedicconnect.authentication;


import com.example.oasismedicconnect.configuration.jwt_configuration.JwtTokenFilter;
import com.example.oasismedicconnect.configuration.jwt_configuration.JwtUtils;
import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Slf4j
public class AuthenticationConfig {

    private UrlBasedCorsConfigurationSource source;
    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain authorizationFilter(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.maximumSessions(1)
                        .expiredUrl("/login")
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeRequests(requests -> {
                            try {
                                requests
                                        .requestMatchers(("/admin/**")).hasRole("ADMIN")
                                        .requestMatchers("/api/register/supplier","/api/register/customer", "/api/login", "/images/**","/api/verify-customer-token/**","/api/test/**", "/api/token-data", "/api/customer-data").permitAll()
                                        .requestMatchers("/appointmentById/**").permitAll()
                                        .anyRequest().authenticated()
                                        .and()
                                        .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                                        .sessionManagement((sessionManagement) ->
                                                sessionManagement.
                                                        sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                                        .maximumSessions(1)
                                                        .expiredUrl("/login?expired"))

                                ;
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform-login")
                        .defaultSuccessUrl("/dashboard")
                        .failureForwardUrl("/login?error=true")

                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }


    @Bean
    public Filter jwtTokenFilter() {
        return new JwtTokenFilter(jwtUtil, customUserDetailsService);
    }




    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, @Qualifier("myPasswordEncoder") PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        System.out.println("Loading object " + authProvider);

        authProvider.setUserDetailsService(userDetailsService);
        System.out.println("authProvider.setUserDetailsService(userDetailsService) " + "Loaded 1 ");

        authProvider.setPasswordEncoder(passwordEncoder);
        System.out.println("authProvider.setUserDetailsService(userDetailsService) " + "Loaded 2 ");
        return new ProviderManager(authProvider);
    }


    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;

    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");  // Allow only this origin
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean(name = "myPasswordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        DelegatingPasswordEncoder delPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
        return delPasswordEncoder;
    }

    @Bean

    public HttpFirewall firewall() {
        return new DefaultHttpFirewall();
    }

}
