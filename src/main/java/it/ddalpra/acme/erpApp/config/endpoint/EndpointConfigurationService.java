package it.ddalpra.acme.erpApp.config.endpoint;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EndpointConfigurationService {

    private final WebServiceEndpointRepository repository;

    public EndpointConfigurationService(WebServiceEndpointRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves the base URL for a given service name.
     * The result is cached to avoid frequent database lookups.
     * @param serviceName The unique name of the service (e.g., "LOGISTIC_API").
     * @return The base URL.
     */
    @Cacheable("endpoints")
    public String getBaseUrl(String serviceName) {
        return repository.findByServiceNameAndEnabled(serviceName, true)
                .map(WebServiceEndpoint::getBaseUrl)
                .orElseThrow(() -> new IllegalStateException("No enabled endpoint configuration found for service: " + serviceName));
    }

    public List<WebServiceEndpoint> getAllEndpoints() {
        return repository.findAll();
    }

    public Optional<WebServiceEndpoint> getEndpointById(Long id) {
        return repository.findById(id);
    }

    @CacheEvict(value = "endpoints", allEntries = true)
    public WebServiceEndpoint saveEndpoint(WebServiceEndpoint endpoint) {
        return repository.save(endpoint);
    }

    @CacheEvict(value = "endpoints", allEntries = true)
    public void deleteEndpoint(Long id) {
        repository.deleteById(id);
    }
}
