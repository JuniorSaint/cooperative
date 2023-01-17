package br.com.cooperative.configs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class UsefulMethods {
    @Autowired
    private ModelMapper mapper;

    public <D, T> Page<D> mapEntityPageIntoDtoPage(Page<T> entities, Class<D> dtoClass) {
        return entities.map(objectEntity -> mapper.map(objectEntity, dtoClass));
    }

    public <S, T> List<T> mapListIntoDtoList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }


    public <T> Page<T> convertListIntoPage(List<T> source) {
        return new PageImpl<>(source);
    }

    public String justNumberAllowed(String str) {
        return str.replaceAll("\\D", "");
    }

}
