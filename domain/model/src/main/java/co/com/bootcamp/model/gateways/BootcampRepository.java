package co.com.bootcamp.model.gateways;

import co.com.bootcamp.model.bootcamp.Bootcamp;
import reactor.core.publisher.Mono;

public interface BootcampRepository {

  Mono<Bootcamp> save(Bootcamp bootcamp);
}
