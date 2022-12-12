package ua.pp.kusochok.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Todo check if header is null
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);

                // Todo remove null check because we catch exception

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            HttpServletResponse response = (HttpServletResponse) servletResponse;
//            response.setContentType("application/json");
            response.setStatus(e.getHttpStatus().value());
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
            System.out.println(e.getMessage());
//            response.getWriter().flush();
        } catch (UsernameNotFoundException e) {
//            TODO Normal Exception handling
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
//            response.getWriter().flush();
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}