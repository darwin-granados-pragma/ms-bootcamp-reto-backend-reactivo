package co.com.bootcamp.model.bootcamp;

import co.com.bootcamp.model.capacity.CapacityResponse;
import java.time.LocalDate;
import java.util.List;
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
public class BootcampResponse {

  private String id;
  private String name;
  private String description;
  private LocalDate releaseDate;
  private Integer duration;
  private List<CapacityResponse> capacityResponses;
}
