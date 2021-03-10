package net.carlosfe.tests.sintad.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import net.carlosfe.tests.sintad.global.models.ErrorResponse;
import net.carlosfe.tests.sintad.global.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -1328798036700325337L;

    @Autowired
    private MessageUtil messageUtil;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(messageUtil.getMessage("message.AuthenticationException"));
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new StdDateFormat().withColonInTimeZone(true);
        mapper.setDateFormat(df);
        mapper.writeValue(out, response);
        out.flush();
    }
}
