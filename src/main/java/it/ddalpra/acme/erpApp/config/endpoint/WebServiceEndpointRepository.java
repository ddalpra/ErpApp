package it.ddalpra.acme.erpApp.config.endpoint;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebServiceEndpointRepository extends JpaRepository<WebServiceEndpoint, Long> {

    Optional<WebServiceEndpoint> findByServiceNameAndEnabled(String serviceName, boolean enabled);
}
