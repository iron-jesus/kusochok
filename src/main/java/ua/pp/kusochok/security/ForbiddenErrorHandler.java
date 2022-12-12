package ua.pp.kusochok.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForbiddenErrorHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(403);
            response.getWriter().print("{\"error\": \"НЕМА ПРАВ У ТЕБЕ ЩЕ, НЕ ДОРІС, МАЛИЙ КУРДУПЕЛЬ\"}");
            response.getWriter().flush();
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
