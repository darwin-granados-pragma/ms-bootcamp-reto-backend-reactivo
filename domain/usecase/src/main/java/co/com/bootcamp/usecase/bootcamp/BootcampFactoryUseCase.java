package co.com.bootcamp.usecase.bootcamp;

import co.com.bootcamp.model.bootcamp.BootcampSortBy;
import co.com.bootcamp.model.error.ErrorCode;
import co.com.bootcamp.model.exception.BusinessException;
import co.com.bootcamp.model.input.BootcampRetrieveStrategy;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BootcampFactoryUseCase {

  private final Map<BootcampSortBy, BootcampRetrieveStrategy> strategyMap;

  public BootcampFactoryUseCase(List<BootcampRetrieveStrategy> strategies) {
    this.strategyMap = strategies
        .stream()
        .collect(Collectors.toMap(BootcampRetrieveStrategy::getType, Function.identity()));
  }

  public BootcampRetrieveStrategy findStrategy(BootcampSortBy type) {
    BootcampRetrieveStrategy strategy = strategyMap.get(type);
    if (strategy == null) {
      throw new BusinessException(ErrorCode.INVALID_SORT_BY);
    }
    return strategy;
  }
}
