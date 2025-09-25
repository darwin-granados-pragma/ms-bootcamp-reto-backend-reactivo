package co.com.bootcamp.model.gateways;

import co.com.bootcamp.model.capacity.CapacityResponse;
import java.util.Set;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CapacityGateway {

  Mono<Void> assignCapacitiesToBootcamp(String idBootcamp, Set<String> capacities);

  Mono<Void> validateCapacities(Set<String> capacities);

  Flux<CapacityResponse> getCapacitiesByIdBootcamp(String idBootcamp);
}
