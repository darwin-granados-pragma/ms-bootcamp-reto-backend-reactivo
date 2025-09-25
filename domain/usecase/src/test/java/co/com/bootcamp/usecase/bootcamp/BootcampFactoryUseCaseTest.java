package co.com.bootcamp.usecase.bootcamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import co.com.bootcamp.model.bootcamp.BootcampSortBy;
import co.com.bootcamp.model.exception.BusinessException;
import co.com.bootcamp.model.input.BootcampRetrieveStrategy;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BootcampFactoryUseCaseTest {

  @Mock
  private BootcampRetrieveStrategy sortByNameStrategy;

  @Mock
  private BootcampRetrieveStrategy sortByTechCountStrategy;

  private BootcampFactoryUseCase bootcampFactoryUseCase;

  @BeforeEach
  void setUp() {
    when(sortByNameStrategy.getType()).thenReturn(BootcampSortBy.NAME);
  }

  @Test
  void shouldReturnCorrectStrategyWhenExists() {
    // Arrange
    bootcampFactoryUseCase = new BootcampFactoryUseCase(List.of(sortByNameStrategy,
        sortByTechCountStrategy
    ));

    // Act
    BootcampRetrieveStrategy foundStrategy = bootcampFactoryUseCase.findStrategy(BootcampSortBy.NAME);

    // Assert
    assertNotNull(foundStrategy);
    assertEquals(BootcampSortBy.NAME, foundStrategy.getType());
    assertEquals(sortByNameStrategy, foundStrategy);
  }

  @Test
  void shouldThrowExceptionWhenStrategyNotFound() {
    // Arrange
    bootcampFactoryUseCase = new BootcampFactoryUseCase(List.of(sortByNameStrategy));

    // Act & Assert
    assertThrows(BusinessException.class,
        () -> bootcampFactoryUseCase.findStrategy(BootcampSortBy.CAP_COUNT)
    );
  }
}