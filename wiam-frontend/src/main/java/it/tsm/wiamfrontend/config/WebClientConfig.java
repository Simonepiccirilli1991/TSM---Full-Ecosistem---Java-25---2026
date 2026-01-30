package it.tsm.wiamfrontend.config;

import it.tsm.wiamfrontend.exception.BackendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${wiam.backend.url}")
    private String backendUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(backendUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter(errorHandlingFilter())
                .build();
    }

    private ExchangeFilterFunction errorHandlingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                int statusCode = clientResponse.statusCode().value();

                return clientResponse.bodyToMono(String.class)
                        .defaultIfEmpty("")
                        .flatMap(errorBody -> {
                            try {
                                // Tenta di parsare come BackendErrorResponse
                                log.error("Backend error - Status: {}, Body: {}", statusCode, errorBody);

                                if (errorBody.isEmpty()) {
                                    return Mono.error(createFallbackException(statusCode));
                                }

                                // Per ora usiamo il fallback, il parsing JSON verrà gestito dal WebClient
                                return Mono.error(createFallbackException(statusCode));

                            } catch (Exception e) {
                                log.warn("Could not parse error response: {}", e.getMessage());
                                return Mono.error(createFallbackException(statusCode));
                            }
                        });
            }
            return Mono.just(clientResponse);
        });
    }

    private BackendException createFallbackException(int statusCode) {
        if (statusCode >= 500) {
            return new BackendException(statusCode, "Si è verificato un errore nel server. Riprova più tardi.");
        } else {
            return new BackendException(statusCode, "Si è verificato un errore. Verifica i dati inseriti.");
        }
    }
}
