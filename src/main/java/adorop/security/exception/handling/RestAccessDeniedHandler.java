package adorop.security.exception.handling;

import adorop.dto.BadRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private final BadRequestProcessor badRequestProcessor;

    @Autowired
    public RestAccessDeniedHandler(BadRequestProcessor badRequestProcessor) {
        this.badRequestProcessor = badRequestProcessor;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        badRequestProcessor.respond(new BadRequestDto(accessDeniedException.getMessage()), request, response);
    }
}
