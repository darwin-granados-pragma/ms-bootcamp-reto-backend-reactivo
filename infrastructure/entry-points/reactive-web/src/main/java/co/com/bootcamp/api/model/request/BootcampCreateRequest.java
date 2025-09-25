package co.com.bootcamp.api.model.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class BootcampCreateRequest {

  @NotBlank(message = "El nombre es obligatorio")
  @Size(max = 50, message = "El nombre debe tener menos de 50 caracteres")
  private String name;

  @NotBlank(message = "La descripción es obligatoria")
  @Size(max = 90, message = "La descripción debe tener menos de 90 caracteres")
  private String description;

  @NotNull(message = "Las capacidades son obligatorias")
  private Set<String> idCapacities;

  @NotNull(message = "La fecha de lanzamiento es obligatoria")
  @Future(message = "La fecha de lanzamiento debe ser una fecha futura")
  private LocalDate releaseDate;

  @NotNull(message = "La duración es obligatoria")
  private Integer duration;
}
