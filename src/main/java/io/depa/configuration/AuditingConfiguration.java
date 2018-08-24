package io.depa.configuration;

import io.depa.security.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfiguration {
    @Bean
    public AuditorAware<Integer> auditorProvider() {
        return new SpringSecurityAuditAware();
    }

    private class SpringSecurityAuditAware implements AuditorAware<Integer> {
        @Override
        public Optional<Integer> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null ||
                    !authentication.isAuthenticated() ||
                    authentication instanceof AnonymousAuthenticationToken) {
                return Optional.empty();
            }

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            return Optional.of(customUserDetails.getId());
        }
    }
}
