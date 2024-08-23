package org.okten.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@EnableMongoAuditing
public class AuditingConfig {

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }

    @Bean
    public AuditorAware<String> auditorAware() {
//        return () -> Optional.of(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaim("sub"));
        return () -> Optional.of("test");
    }
}
