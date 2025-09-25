package co.com.bootcamp.usecase.bootcamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import co.com.bootcamp.model.bootcamp.Bootcamp;
import co.com.bootcamp.model.bootcamp.BootcampSortBy;
import co.com.bootcamp.model.capacity.CapacityResponse;
import co.com.bootcamp.model.capacity.technology.Technology;
import co.com.bootcamp.model.gateways.BootcampRepository;
import co.com.bootcamp.model.gateways.CapacityGateway;
import co.com.bootcamp.model.page.BootcampPageCommand;
import co.com.bootcamp.model.page.SortDirection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BootcampSortByNameUseCaseTest {

  @Mock
  private BootcampRepository repository;

  @Mock
  private CapacityGateway capacityGateway;

  @InjectMocks
  private BootcampSortByNameUseCase bootcampSortByNameUseCase;

  private BootcampPageCommand command;
  private CapacityResponse capacityResponse;
  private Bootcamp bootcamp;

  @BeforeEach
  void setUp() {
    command = BootcampPageCommand
        .builder()
        .page(0)
        .size(10)
        .sortBy(BootcampSortBy.NAME)
        .sortDirection(SortDirection.ASC)
        .build();
    bootcamp = Bootcamp
        .builder()
        .id("boot test id")
        .name("boot test name")
        .duration(11)
        .build();
    Technology technology = Technology
        .builder()
        .id("tech test id")
        .name("tech test name")
        .build();
    capacityResponse = CapacityResponse
        .builder()
        .id("cap test id")
        .name("cap test name")
        .technologies(List.of(technology))
        .build();
  }

  @Test
  void shouldReturnTypeCorrectly() {
    // Assert
    assertEquals(BootcampSortBy.NAME, bootcampSortByNameUseCase.getType());
  }

  @Test
  void shouldReturnPaginatedResponseWhenBootcampsExist() {
    // Arrange
    when(repository.getTotalCount()).thenReturn(Mono.just(1L));
    when(repository.findAllOrderByName(command)).thenReturn(Flux.just(bootcamp));
    when(capacityGateway.getCapacitiesByIdBootcamp(bootcamp.getId())).thenReturn(Flux.just(
        capacityResponse));

    // Act
    var result = bootcampSortByNameUseCase.getBootcampResponse(command);

    // Assert
    StepVerifier
        .create(result)
        .expectNextMatches(pageResponse -> pageResponse.getTotalElements() == 1 && pageResponse
            .getContent()
            .get(0)
            .getCapacityResponses()
            .size() == 1)
        .verifyComplete();
  }

  @Test
  void shouldReturnEmptyPageResponseWhenNoCapacitiesExist() {
    // Arrange
    when(repository.getTotalCount()).thenReturn(Mono.just(0L));
    when(repository.findAllOrderByName(command)).thenReturn(Flux.empty());

    // Act
    var result = bootcampSortByNameUseCase.getBootcampResponse(command);

    // Assert
    StepVerifier
        .create(result)
        .expectNextMatches(pageResponse -> pageResponse.getTotalElements() == 0
            && pageResponse.getTotalPages() == 0 && pageResponse
            .getContent()
            .isEmpty())
        .verifyComplete();
  }
}