package com.project.config;

import com.project.jwt.JwtAuthenticationEntryPoint;
import com.project.jwt.JwtConfigurer;
import com.project.jwt.JwtTokenProvider;
import com.project.security.CustomOAuth2UserService;
import com.project.service.impl.CustomUserDetailsService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oauth2UserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth/**", "/auth/**", "/login").permitAll()// Secure endpoints
                .anyRequest().authenticated() // Allow other endpoints
                .and()
                .oauth2Login().userInfoEndpoint().userService(oauth2UserService)
                .and()
                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001",
                "http://localhost:3002", "https://it-talent-hunt.github.io"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "PATCH", "POST", "DELETE",
                "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type",
                "Accept", "Authorization", "Access-Control-Allow-Credentials",
                "Access-Control-Allow-Headers", "Access-Control-Allow-Methods",
                "Access-Control-Allow-Origin", "Access-Control-Expose-Headers",
                "Access-Control-Max-Age", "Access-Control-Request-Headers",
                "Access-Control-Request-Method",
                "Age", "Allow", "Alternates", "Content-Range", "Content-Disposition",
                "Content-Description"));
        configuration.setExposedHeaders(Arrays.asList("/**"));
        configuration.setAllowCredentials(true);
        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
