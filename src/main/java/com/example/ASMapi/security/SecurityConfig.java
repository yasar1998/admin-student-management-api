package com.example.ASMapi.security;

import com.example.ASMapi.security.filters.JwtFilter;
import com.example.ASMapi.security.handler.AuthEntryPoint;
import com.example.ASMapi.security.handler.AuthorizedResourceHandler;
import com.example.ASMapi.security.utils.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService loginService;

    private final JwtFilter jwtFilter;

    private final AuthEntryPoint authEntryPoint;

    private final AuthorizedResourceHandler authorizedResourceHandler;

    private final PasswordEncoder passwordEncoder;


    public SecurityConfig(UserDetailsService loginService, JwtFilter jwtFilter, AuthEntryPoint authEntryPoint, AuthorizedResourceHandler authorizedResourceHandler, PasswordEncoder passwordEncoder) {
        this.loginService = loginService;
        this.jwtFilter = jwtFilter;
        this.authEntryPoint = authEntryPoint;
        this.authorizedResourceHandler = authorizedResourceHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/api/**").hasAnyRole(Roles.ADMIN, Roles.STUDENT)
                .antMatchers("/admin/**").hasAnyRole(Roles.ADMIN)
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/swagger-ui/**", "/swagger-resources", "/swagger-resources/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated();

        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .accessDeniedHandler(authorizedResourceHandler);
    }

}
