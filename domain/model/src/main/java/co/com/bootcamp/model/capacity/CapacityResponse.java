package co.com.bootcamp.model.capacity;

import co.com.bootcamp.model.capacity.technology.Technology;
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
public class CapacityResponse {

  private String id;
  private String name;
  private List<Technology> technologies;
}
