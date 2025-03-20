package org.limckmy.geodistancecalculator.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.limckmy.geodistancecalculator.auth.AuthException;
import org.limckmy.geodistancecalculator.auth.JWTUtil;
import org.limckmy.geodistancecalculator.config.SecurityConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private boolean isWhiltelisted(String uri) {
        for (String path : SecurityConfig.WHITELIST) {
            if (uri.contains(path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isWhiltelisted(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        Claims claims = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            try {
                claims = jwtUtil.extractClaims(jwt);
                username = claims.getSubject();
            } catch (Exception e) {

                unauthorized(response);
                return;
            }
        }

        if (claims == null) {
            unauthorized(response);
            return;
        }

        List<GrantedAuthority> authorities = jwtUtil.getAuthoritiesFromToken(claims);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private void unauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        AuthException apiError = new AuthException(HttpStatus.UNAUTHORIZED, "Unauthorized", "The provided token is either invalid, expired, or missing. Please ensure you are using a valid token and try again.");
        ObjectMapper mapper = new ObjectMapper(); String json = mapper.writeValueAsString(apiError);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
