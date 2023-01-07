package com.application.security.filters;

import com.application.security.jwt.JWTTokenProvider;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.List;

import static com.application.constants.SecurityConstants.OPTIONS_HTTP_METHOD;
import static com.application.constants.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Component("jwtAuthorizationFilterBean")
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTTokenProvider jwtTokenProviderBean;

    @Autowired
    public JWTAuthorizationFilter(JWTTokenProvider jwtTokenProviderBean) { this.jwtTokenProviderBean = jwtTokenProviderBean; }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)){

            response.setStatus(OK.value());

        }else if(authorizationHeader!=null && authorizationHeader.startsWith(TOKEN_PREFIX)){

            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username = jwtTokenProviderBean.getSubject(token);

            if(jwtTokenProviderBean.isTokenValid(username, token)){
                List<GrantedAuthority> authorities = jwtTokenProviderBean.getAuthorities(token);
                Authentication authentication = jwtTokenProviderBean.getAuthentication(username, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);

    }
}
