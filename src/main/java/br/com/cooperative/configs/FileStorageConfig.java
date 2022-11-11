package br.com.cooperative.configs;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FileStorageConfig {
    private String uploadDir;
}
