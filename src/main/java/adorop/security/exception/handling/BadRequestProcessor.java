package adorop.security.exception.handling;

import adorop.dto.BadRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

interface BadRequestProcessor {
    void respond(BadRequestDto payload, HttpServletRequest request, HttpServletResponse response);
}
