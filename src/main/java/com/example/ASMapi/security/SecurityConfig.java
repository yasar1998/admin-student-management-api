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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


    public SecurityConfig(UserDetailsService loginService, JwtFilter jwtFilter, AuthEntryPoint authEntryPoint, AuthorizedResourceHandler authorizedResourceHandler) {
        this.loginService = loginService;
        this.jwtFilter = jwtFilter;
        this.authEntryPoint = authEntryPoint;
        this.authorizedResourceHandler = authorizedResourceHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
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
                .antMatchers("/admin/**").hasAnyRole(Roles.ADMIN);

        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .accessDeniedHandler(authorizedResourceHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
