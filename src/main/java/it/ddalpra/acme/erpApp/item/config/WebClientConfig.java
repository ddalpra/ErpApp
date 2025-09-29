package it.ddalpra.acme.erpApp.item.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import it.ddalpra.acme.erpApp.config.endpoint.EndpointConfigurationService;

@Configuration
public class WebClientConfig {


    // Cache per le istanze di WebClient già create
    private final Map<String, WebClient> webClients = new ConcurrentHashMap<>();
    private final EndpointConfigurationService endpointService;

    public WebClientConfig(EndpointConfigurationService endpointService) {
        this.endpointService = endpointService;
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    public WebClient getWebClient(String serviceName) {
        return webClients.computeIfAbsent(serviceName, name -> {
            String baseUrl = endpointService.getBaseUrl(name);
            return webClientBuilder()
                    .baseUrl(baseUrl)
                    .build();
        });
    }
}
