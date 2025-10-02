package co.com.bootcamp.model.report;

import java.time.LocalDate;
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
public class Report {

  private String idBootcamp;
  private String name;
  private String description;
  private LocalDate releaseDate;
  private Integer duration;
}
