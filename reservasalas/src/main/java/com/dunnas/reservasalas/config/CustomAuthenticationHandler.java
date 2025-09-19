package com.dunnas.reservasalas.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(authentication);

        if(response.isCommitted()) {
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);

    }

    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for(GrantedAuthority authority : authorities) {
            if(authority.getAuthority().equals("ROLE_ADMINISTRADOR")) {
                return "/home/adm";
            } else if(authority.getAuthority().equals("ROLE_CLIENTE") ||
              authority.getAuthority().equals("ROLE_RECEPCIONISTA")) {
                return "/home/client-receptionist";
            }
        }
        return "/home";
    }
}
