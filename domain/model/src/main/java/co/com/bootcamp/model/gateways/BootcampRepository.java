package co.com.bootcamp.model.gateways;

import co.com.bootcamp.model.bootcamp.Bootcamp;
import co.com.bootcamp.model.page.BootcampPageCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootcampRepository {

  Mono<Bootcamp> save(Bootcamp bootcamp);

  Flux<Bootcamp> findAllOrderByName(BootcampPageCommand command);

  Mono<Long> getTotalCount();

  Mono<Void> deleteById(String id);
}
