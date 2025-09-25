package co.com.bootcamp.usecase.bootcamp;

import co.com.bootcamp.model.bootcamp.Bootcamp;
import co.com.bootcamp.model.bootcamp.BootcampResponse;
import co.com.bootcamp.model.bootcamp.BootcampSortBy;
import co.com.bootcamp.model.gateways.BootcampRepository;
import co.com.bootcamp.model.gateways.CapacityGateway;
import co.com.bootcamp.model.input.BootcampRetrieveStrategy;
import co.com.bootcamp.model.page.BootcampPageCommand;
import co.com.bootcamp.model.page.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BootcampSortByNameUseCase implements BootcampRetrieveStrategy {

  private final BootcampRepository repository;
  private final CapacityGateway capacityGateway;

  @Override
  public BootcampSortBy getType() {
    return BootcampSortBy.NAME;
  }

  @Override
  public Mono<PageResponse<BootcampResponse>> getBootcampResponse(BootcampPageCommand command) {
    return repository
        .getTotalCount()
        .flatMap(total -> getBootcampResponses(command)
            .collectList()
            .flatMap(bootcampResponses -> buildPageResponse(bootcampResponses, command, total)));
  }

  private Flux<BootcampResponse> getBootcampResponses(BootcampPageCommand command) {
    return repository
        .findAllOrderByName(command)
        .flatMap(this::mapToBootcampResponse);
  }

  private Mono<BootcampResponse> mapToBootcampResponse(Bootcamp bootcamp) {
    return capacityGateway
        .getCapacitiesByIdBootcamp(bootcamp.getId())
        .collectList()
        .map(responseList -> BootcampResponse
            .builder()
            .id(bootcamp.getId())
            .name(bootcamp.getName())
            .description(bootcamp.getDescription())
            .releaseDate(bootcamp.getReleaseDate())
            .duration(bootcamp.getDuration())
            .capacityResponses(responseList)
            .build());
  }

  private Mono<PageResponse<BootcampResponse>> buildPageResponse(List<BootcampResponse> content,
      BootcampPageCommand command, long totalElements) {
    return Mono.defer(() -> {
      int totalPages = (int) Math.ceil((double) totalElements / command.getSize());
      return Mono.just(PageResponse
          .<BootcampResponse>builder()
          .content(content)
          .page(command.getPage())
          .size(command.getSize())
          .totalElements(totalElements)
          .totalPages(totalPages)
          .build());
    });
  }
}
