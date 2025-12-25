package com.skydiveforecast.infrastructure.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter dropzoneCreationCounter(MeterRegistry registry) {
        return Counter.builder("dropzone.creation")
                .description("Total number of dropzones created")
                .tag("service", "location")
                .register(registry);
    }

    @Bean
    public Counter dropzoneUpdateCounter(MeterRegistry registry) {
        return Counter.builder("dropzone.update")
                .description("Total number of dropzone updates")
                .tag("service", "location")
                .register(registry);
    }

    @Bean
    public Counter dropzoneDeletionCounter(MeterRegistry registry) {
        return Counter.builder("dropzone.deletion")
                .description("Total number of dropzones deleted")
                .tag("service", "location")
                .register(registry);
    }

    @Bean
    public Counter dropzoneQueryCounter(MeterRegistry registry) {
        return Counter.builder("dropzone.query")
                .description("Total number of dropzone queries")
                .tag("service", "location")
                .register(registry);
    }
}
