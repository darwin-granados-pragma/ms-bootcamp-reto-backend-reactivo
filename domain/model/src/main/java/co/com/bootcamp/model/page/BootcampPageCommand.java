package co.com.bootcamp.model.page;

import co.com.bootcamp.model.bootcamp.BootcampSortBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BootcampPageCommand {

  private int page;
  private int size;
  private BootcampSortBy sortBy;
  private SortDirection sortDirection;
}
