package adorop.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor @Getter
@JacksonXmlRootElement(localName = "error")
public class BadRequestDto {
    private String message;

    public BadRequestDto(String message) {
        this.message = message;
    }
}
