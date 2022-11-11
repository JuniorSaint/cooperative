package br.com.cooperative.configs;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;

/*
This class config what the return pageable will contain
*/

@JsonComponent
@Configuration
public class PageableJsonResponse {

  public static class PageSerializer extends JsonSerializer<PageImpl<?>> {

    @Override
    public void serialize(@NotNull PageImpl<?> page, @NotNull JsonGenerator jsonGenerator, SerializerProvider serializers)
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
