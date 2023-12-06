package io.spring.movie.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ConfigReader {

    @Value("${config.key}")
    private String key;
}
