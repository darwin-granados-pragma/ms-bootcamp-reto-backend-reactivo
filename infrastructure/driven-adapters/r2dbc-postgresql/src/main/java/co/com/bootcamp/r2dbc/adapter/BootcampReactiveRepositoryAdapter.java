package co.com.bootcamp.r2dbc.adapter;

import co.com.bootcamp.model.bootcamp.Bootcamp;
import co.com.bootcamp.model.gateways.BootcampRepository;
import co.com.bootcamp.r2dbc.entity.BootcampEntity;
import co.com.bootcamp.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bootcamp.r2dbc.repository.BootcampReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class BootcampReactiveRepositoryAdapter extends
    ReactiveAdapterOperations<Bootcamp, BootcampEntity, String, BootcampReactiveRepository> implements
    BootcampRepository {

  public BootcampReactiveRepositoryAdapter(BootcampReactiveRepository repository,
      ObjectMapper mapper) {
    super(repository, mapper, d -> mapper.map(d, Bootcamp.class));
  }

  @Override
  public Mono<Bootcamp> save(Bootcamp bootcamp) {
    log.info("Saving bootcamp with name: {}", bootcamp.getName());
    return super
        .save(bootcamp)
        .doOnSuccess(bootcampSaved -> log.debug("Bootcamp saved: {}", bootcampSaved));
  }
}
