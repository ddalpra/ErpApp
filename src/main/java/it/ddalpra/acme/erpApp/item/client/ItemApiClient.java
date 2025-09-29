package it.ddalpra.acme.erpApp.item.client;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import it.ddalpra.acme.erpApp.item.config.WebClientConfig;
import it.ddalpra.acme.erpApp.item.entity.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemApiClient {

    private static final String LOGISTIC_SERVICE_NAME = "LOGISTIC_API";
    private final WebClient webClient;

    public ItemApiClient(WebClientConfig webClientConfig) {
        this.webClient = webClientConfig.getWebClient(LOGISTIC_SERVICE_NAME);
    }

    public Flux<Item> getAllItems() {
        return webClient.get()
                .uri("/items/listall")
                .retrieve()
                .bodyToFlux(Item.class);
    }

    public Mono<Item> getItemById(UUID id) {
        return webClient.get()
                .uri("/items/{id}", id)
                .retrieve()
                .bodyToMono(Item.class);
    }

    public Mono<Item> createItem(Item newItem) {
        return webClient.post()
                .uri("/items")
                .body(Mono.just(newItem), Item.class)
                .retrieve()
                .bodyToMono(Item.class);
    }

    public Mono<Item> updateItem(UUID id, Item itemDetails) {
        return webClient.put()
                .uri("/items/{id}", id)
                .body(Mono.just(itemDetails), Item.class)
                .retrieve()
                .bodyToMono(Item.class);
    }

    public Mono<Void> deleteItem(UUID id) {
        return webClient.delete()
                .uri("/items/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}