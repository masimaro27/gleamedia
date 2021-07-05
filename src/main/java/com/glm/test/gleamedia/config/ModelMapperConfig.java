package com.glm.test.gleamedia.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private final ModelMapper modelMapper = new ModelMapper();

    @Bean
    public ModelMapper globalModelMapper() {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

}
