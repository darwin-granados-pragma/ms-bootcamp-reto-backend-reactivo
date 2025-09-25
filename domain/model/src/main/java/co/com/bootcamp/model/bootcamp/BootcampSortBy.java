package co.com.bootcamp.model.bootcamp;

import co.com.bootcamp.model.error.ErrorCode;
import co.com.bootcamp.model.exception.InvalidInputException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BootcampSortBy {
  NAME("name"),
  CAP_COUNT("capCount");

  private final String displayName;

  public static BootcampSortBy getSortBy(String input) {
    return Arrays
        .stream(values())
        .filter(e -> e
            .name()
            .equalsIgnoreCase(input) || e.displayName.equalsIgnoreCase(input))
        .findFirst()
        .orElseThrow(() -> new InvalidInputException(ErrorCode.INVALID_SORT_BY));
  }
}
