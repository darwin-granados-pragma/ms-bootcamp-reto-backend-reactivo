package co.com.bootcamp.usecase.bootcamp;

import co.com.bootcamp.model.bootcamp.Bootcamp;
import co.com.bootcamp.model.bootcamp.BootcampCreate;
import co.com.bootcamp.model.bootcamp.BootcampResponse;
import co.com.bootcamp.model.bootcamp.BootcampSortBy;
import co.com.bootcamp.model.error.ErrorCode;
import co.com.bootcamp.model.exception.BusinessException;
import co.com.bootcamp.model.gateways.BootcampRepository;
import co.com.bootcamp.model.gateways.CapacityGateway;
import co.com.bootcamp.model.gateways.TransactionalGateway;
import co.com.bootcamp.model.input.BootcampRetrieveStrategy;
import co.com.bootcamp.model.page.BootcampPageCommand;
import co.com.bootcamp.model.page.PageResponse;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BootcampUseCase {

  private final BootcampRepository repository;
  private final CapacityGateway capacityGateway;
  private final TransactionalGateway transactionalGateway;
  private final BootcampFactoryUseCase factoryUseCase;

  public Mono<Bootcamp> createBootcamp(BootcampCreate data) {
    return validateCapacitiesSize(data.getIdCapacities())
        .then(validateIdCapacities(data.getIdCapacities()))
        .then(buildAndSave(data))
        .flatMap(bootcamp -> capacityGateway
            .assignCapacitiesToBootcamp(bootcamp.getId(), data.getIdCapacities())
            .thenReturn(bootcamp))
        .as(transactionalGateway::execute);
  }

  public Mono<PageResponse<BootcampResponse>> getBootcampResponses(BootcampPageCommand command) {
    return Mono.defer(() -> {
      BootcampSortBy sortBy = command.getSortBy();
      BootcampRetrieveStrategy strategy = factoryUseCase.findStrategy(sortBy);
      return strategy.getBootcampResponse(command);
    });
  }

  private Mono<Void> validateCapacitiesSize(Set<String> capacities) {
    if (capacities.isEmpty() || capacities.size() > 4) {
      return Mono.error(new BusinessException(ErrorCode.BOOTCAMP_CAPACITY_SIZE));
    }
    return Mono.empty();
  }

  private Mono<Void> validateIdCapacities(Set<String> capacities) {
    return Mono.defer(() -> capacityGateway.validateCapacities(capacities));
  }

  private Mono<Bootcamp> buildAndSave(BootcampCreate data) {
    return Mono
        .fromCallable(() -> Bootcamp
            .builder()
            .id(UUID
                .randomUUID()
                .toString())
            .name(data.getName())
            .description(data.getDescription())
            .releaseDate(data.getReleaseDate())
            .duration(data.getDuration())
            .isNew(true)
            .build())
        .flatMap(repository::save);
  }
}
