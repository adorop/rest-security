package adorop;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

/**
 * Created by user on 09.05.2017.
 */
public class PostmanTest {

    public static final RestTemplate REST_TEMPLATE = new RestTemplate();

    @Test
    public void name() throws Exception {
        try {

            LinkedMultiValueMap<String, String> linkedMultiValueMap = getStringStringLinkedMultiValueMap("123");
            RequestEntity requestEntity = getObjectRequestEntity(linkedMultiValueMap);
            getExchange(requestEntity);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
        }

        try {
            getExchange(getObjectRequestEntity(getStringStringLinkedMultiValueMap("12")));
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
        }

        try {
            getExchange(getObjectRequestEntity(new LinkedMultiValueMap<>()));
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
        }
    }

    private RequestEntity<Object> getObjectRequestEntity(LinkedMultiValueMap<String, String> linkedMultiValueMap) {
        return new RequestEntity<>(null, linkedMultiValueMap, HttpMethod.GET, URI.create("http://localhost:8080/users/1"));
    }

    private ResponseEntity<String> getExchange(RequestEntity requestEntity) {
        return REST_TEMPLATE.exchange("http://localhost:8080/users/2", HttpMethod.GET, requestEntity, String.class);
    }

    private LinkedMultiValueMap<String, String> getStringStringLinkedMultiValueMap(String token) {
        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.put("Authorization", Collections.singletonList(token));
        linkedMultiValueMap.put("Accept", Collections.singletonList("application/xml"));
        return linkedMultiValueMap;
    }
}
