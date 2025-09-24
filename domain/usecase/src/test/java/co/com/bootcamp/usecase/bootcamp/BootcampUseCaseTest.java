package co.com.bootcamp.usecase.bootcamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.com.bootcamp.model.bootcamp.Bootcamp;
import co.com.bootcamp.model.bootcamp.BootcampCreate;
import co.com.bootcamp.model.exception.BusinessException;
import co.com.bootcamp.model.gateways.BootcampRepository;
import co.com.bootcamp.model.gateways.CapacityGateway;
import co.com.bootcamp.model.gateways.TransactionalGateway;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BootcampUseCaseTest {


  @Mock
  private BootcampRepository repository;
  @Mock
  private CapacityGateway capacityGateway;
  @Mock
  private TransactionalGateway transactionalGateway;

  private Set<String> capacities;
  private BootcampCreate createData;

  @InjectMocks
  private BootcampUseCase useCase;

  private static Stream<Set<String>> provideInvalidCapacitySizes() {
    return Stream.of(Collections.emptySet(), Set.of("c1", "c2", "c3", "c4", "c5"));
  }

  @BeforeEach
  void setUp() {
    capacities = Set.of("capacity1", "capacity2", "capacity3");
    createData = BootcampCreate
        .builder()
        .name("test name")
        .description("test description")
        .idCapacities(capacities)
        .build();
  }

  @Test
  void shouldCreateBootcampAndAssignCapacities() {
    // Arrange
    var bootcampSaved = Bootcamp
        .builder()
        .id("boot123")
        .name(createData.getName())
        .description(createData.getDescription())
        .build();
    when(capacityGateway.validateCapacities(capacities)).thenReturn(Mono.empty());
    when(repository.save(any(Bootcamp.class))).thenReturn(Mono.just(bootcampSaved));
    when(capacityGateway.assignCapacitiesToBootcamp(bootcampSaved.getId(), capacities)).thenReturn(
        Mono.empty());
    when(transactionalGateway.execute(ArgumentMatchers.<Mono<?>>any())).thenAnswer(invocation -> invocation.getArgument(
        0));

    // Act
    var resultMono = useCase.createBootcamp(createData);

    // Assert
    StepVerifier
        .create(resultMono)
        .expectNext(bootcampSaved)
        .verifyComplete();

    verify(capacityGateway).validateCapacities(capacities);
    verify(repository).save(any(Bootcamp.class));
    verify(capacityGateway).assignCapacitiesToBootcamp(bootcampSaved.getId(), capacities);
    verify(transactionalGateway).execute(any());
  }

  @ParameterizedTest
  @MethodSource("provideInvalidCapacitySizes")
  void shouldReturnErrorWhenCapacitySizeIsInvalid(Set<String> invalidCapacityIds) {
    // Arrange
    createData.setIdCapacities(invalidCapacityIds);
    when(transactionalGateway.execute(ArgumentMatchers.<Mono<?>>any())).thenAnswer(invocation -> invocation.getArgument(
        0));

    // Act
    var resultMono = useCase.createBootcamp(createData);

    // Assert
    StepVerifier
        .create(resultMono)
        .expectError(BusinessException.class)
        .verify();
    verify(repository, never()).save(any());
    verify(capacityGateway, never()).assignCapacitiesToBootcamp(any(), any());
  }
}