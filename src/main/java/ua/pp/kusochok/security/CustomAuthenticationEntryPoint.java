package ua.pp.kusochok.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        res.setContentType("application/json;charset=UTF-8");
//        res.setStatus(HttpStatus.FORBIDDEN.value());
//        System.out.println(authException.getMessage());
//        res.getWriter().print("{\"error\": \"ВИЙШОВ І ЗАЙШОВ НОРМАЛЬНО\"}");
        res.getWriter().flush();
    }
}