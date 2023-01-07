package com.application.security.filters;

import com.application.classes.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static com.application.constants.SecurityConstants.ACCESS_DENIED_MESSAGE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component("jwtAccessDeniedHandlerBean")
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {

        HttpResponse httpResponse = new HttpResponse(UNAUTHORIZED.value(), UNAUTHORIZED, UNAUTHORIZED.getReasonPhrase().toUpperCase(), ACCESS_DENIED_MESSAGE);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(UNAUTHORIZED.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();

    }
}
