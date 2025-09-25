package co.com.bootcamp.consumer;

import co.com.bootcamp.consumer.model.TechnologyRestResponse;
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
public class CapacityRestResponse {

  private String id;
  private String name;
  private List<TechnologyRestResponse> technologies;
}
