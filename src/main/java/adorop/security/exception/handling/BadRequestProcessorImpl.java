package adorop.security.exception.handling;

import adorop.dto.BadRequestDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import utils.Pair;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.function.Predicate;

@Component
class BadRequestProcessorImpl implements BadRequestProcessor {
    private final ContentNegotiationManager contentNegotiationManager;
    private final List<HttpMessageConverter<?>> messageConverters;

    @Autowired
    BadRequestProcessorImpl(RequestMappingHandlerAdapter requestMappingHandlerAdapter,
                                   ContentNegotiationManager contentNegotiationManager) {
        this.contentNegotiationManager = contentNegotiationManager;
        messageConverters = requestMappingHandlerAdapter.getMessageConverters();
    }

    @Override
    @SneakyThrows
    public void respond(BadRequestDto payload, HttpServletRequest request, HttpServletResponse response) {
        List<MediaType> mediaTypes = contentNegotiationManager.resolveMediaTypes(new ServletWebRequest(request));
        Pair<MediaType, HttpMessageConverter<Object>> pair = mediaTypes.stream()
                .filter(mediaType -> messageConverters.stream().anyMatch(canWrite(mediaType)))
                .findFirst()
                .map(mediaType -> new Pair<>(mediaType, (HttpMessageConverter<Object>) messageConverters.stream().filter(canWrite(mediaType)).findFirst().get()))
                .orElse(new Pair<>(MediaType.APPLICATION_JSON, new MappingJackson2HttpMessageConverter()));
        pair.getValue().write(payload, pair.getKey(), new ServletServerHttpResponse(response));
    }

    private Predicate<HttpMessageConverter<?>> canWrite(MediaType mediaType) {
        return httpMessageConverter -> httpMessageConverter.canWrite(BadRequestDto.class, mediaType);
    }
}
