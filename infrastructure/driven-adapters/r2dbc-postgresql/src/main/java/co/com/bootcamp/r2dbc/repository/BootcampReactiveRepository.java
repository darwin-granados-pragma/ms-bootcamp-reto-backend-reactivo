package co.com.bootcamp.r2dbc.repository;

import co.com.bootcamp.r2dbc.entity.BootcampEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BootcampReactiveRepository extends ReactiveCrudRepository<BootcampEntity, String>,
    ReactiveQueryByExampleExecutor<BootcampEntity> {
}
