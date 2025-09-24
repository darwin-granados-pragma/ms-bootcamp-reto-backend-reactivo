package co.com.bootcamp.model.bootcamp;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BootcampCreate {

  private String name;
  private String description;
  private LocalDate releaseDate;
  private Integer duration;
  private Set<String> idCapacities;
}
