package net.carlosfe.tests.sintad.global.filters;

import io.jsonwebtoken.JwtException;
import net.carlosfe.tests.sintad.global.configs.JwtConfig;
import net.carlosfe.tests.sintad.global.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;
    private final JwtConfig jwtConfig;
    private final JwtUtil jwtTokenUtil;

    public JWTAuthorizationFilter(@Qualifier("handlerExceptionResolver") final HandlerExceptionResolver resolver, JwtConfig jwtConfig, JwtUtil jwtTokenUtil) {
        this.resolver = resolver;
        this.jwtConfig = jwtConfig;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(jwtConfig.getHeader());
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(jwtConfig.getPrefix() + " ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (JwtException ex){
                logger.warn(ex.getMessage());
                resolver.resolveException(request, response, null, ex);
                return;
            }
        } else {
            logger.warn("Esta solicitud no tiene token.");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenUtil.validateToken(jwtToken, username)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}
