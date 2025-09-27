package co.com.bootcamp.consumer;

import co.com.bootcamp.consumer.mapper.CapacityMapper;
import co.com.bootcamp.model.capacity.CapacityResponse;
import co.com.bootcamp.model.error.ErrorCode;
import co.com.bootcamp.model.exception.BusinessException;
import co.com.bootcamp.model.exception.CapacityAssignmentException;
import co.com.bootcamp.model.exception.InvalidCapacityException;
import co.com.bootcamp.model.gateways.CapacityGateway;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements CapacityGateway {

  private final WebClient client;
  private final CapacityMapper capacityMapper;

  @Override
  public Mono<Void> assignCapacitiesToBootcamp(String idBootcamp, Set<String> capacities) {
    return client
        .post()
        .uri("/api/v1/bootcamp/{id}/capacities", idBootcamp)
        .bodyValue(capacities)
        .retrieve()
        .onStatus(HttpStatusCode::isError,
            clientResponse -> clientResponse
                .bodyToMono(ErrorResponse.class)
                .flatMap(errorBody -> Mono.error(new CapacityAssignmentException(ErrorCode.CANNOT_ASSIGN_CAPACITIES_TO_BOOTCAMP,
                    errorBody.error()
                )))
        )
        .bodyToMono(Void.class);
  }

  @Override
  public Mono<Void> validateCapacities(Set<String> capacities) {
    return client
        .post()
        .uri("/api/v1/capacity/validate")
        .bodyValue(capacities)
        .retrieve()
        .onStatus(HttpStatusCode::isError,
            clientResponse -> clientResponse
                .bodyToMono(ErrorResponse.class)
                .flatMap(errorBody -> Mono.error(new InvalidCapacityException(ErrorCode.INVALID_CAPACITY,
                    errorBody.error()
                )))
        )
        .bodyToMono(Void.class);
  }

  @Override
  public Flux<CapacityResponse> getCapacitiesByIdBootcamp(String idBootcamp) {
    return client
        .get()
        .uri("/api/v1/bootcamp/{idBootcamp}/capacities", idBootcamp)
        .retrieve()
        .bodyToFlux(CapacityRestResponse.class)
        .map(capacityMapper::toDomain)
        .onErrorResume(WebClientResponseException.NotFound.class, ex -> {
              log.warn("Capacity list not found for idBootcamp={}. The endpoint returned 404.",
                  idBootcamp
              );
              return Flux.empty();
            }
        );
  }

  @Override
  public Mono<Void> deleteRelationsBootcampCapacities(String idBootcamp) {
    return client
        .delete()
        .uri("/api/v1/bootcamp/{idBootcamp}", idBootcamp)
        .retrieve()
        .bodyToMono(Void.class)
        .onErrorResume(WebClientResponseException.InternalServerError.class, ex -> {
              log.error("Error to delete capacities: {}", ex.getMessage());
              return Mono.error(new BusinessException(ErrorCode.CANNOT_POSIBLE_DELETE_CAPACITY_RELATIONS));
            }
        );
  }
}
