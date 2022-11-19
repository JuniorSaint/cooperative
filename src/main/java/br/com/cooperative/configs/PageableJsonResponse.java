package br.com.cooperative.configs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;

@JsonComponent
public class PageableJsonResponse {

    public static class PageSerializer extends JsonSerializer<PageImpl<?>> {

        @Override
        public void serialize(PageImpl<?> page, JsonGenerator jsonGenerator, SerializerProvider serializers)
                throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("items", page.getContent());
            jsonGenerator.writeNumberField("totalPage", page.getTotalPages());
            jsonGenerator.writeNumberField("totalElements", page.getTotalElements());
            jsonGenerator.writeNumberField("currentPage",  page.getNumber());

            jsonGenerator.writeEndObject();
        }
    }
}

