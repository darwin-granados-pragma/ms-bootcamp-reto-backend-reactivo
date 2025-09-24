package co.com.bootcamp.model.gateways;

import java.util.Set;
import reactor.core.publisher.Mono;

public interface CapacityGateway {

  Mono<Void> assignCapacitiesToBootcamp(String idBootcamp, Set<String> capacities);

  Mono<Void> validateCapacities(Set<String> capacities);

}
