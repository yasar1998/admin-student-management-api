package com.example.ASMapi.security.filters;

import com.example.ASMapi.entity.AppUser;
import com.example.ASMapi.exceptions.custom.UserNotFoundException;
import com.example.ASMapi.repository.UserRepository;
import com.example.ASMapi.security.utils.JwtConstants;
import com.example.ASMapi.security.utils.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;

    private final UserRepository userRepository;

    public JwtFilter(TokenManager tokenManager, UserRepository userRepository) {
        this.tokenManager = tokenManager;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String bearerToken = request.getHeader(JwtConstants.HEADER);
        log.info(bearerToken);
        if(bearerToken != null && bearerToken.startsWith(JwtConstants.TOKEN_PREFIX)) {

            String token = bearerToken.replace(JwtConstants.TOKEN_PREFIX, "");

            String username = tokenManager.getUsernameFromToken(token);
            List<GrantedAuthority> authorities = new ArrayList<>();

            AppUser appUser = userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("User not found"));

            appUser.getRoleList().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            log.info("Jwt filter completed");

        }
        log.info("Jwt Checked");
        filterChain.doFilter(request, response);
    }
}
