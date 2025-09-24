package co.com.bootcamp.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  BOOTCAMP_CAPACITY_SIZE("BOOTCAMP-CAPACITY-SIZE",
      ExceptionCode.INVALID_INPUT,
      "Un bootcamp debe tener entre 1 y 4 capacidades"
  ),
  CANNOT_ASSIGN_CAPACITIES_TO_BOOTCAMP("CANNOT-ASSIGN-CAPACITIES-TO-BOOTCAMP",
      ExceptionCode.INVALID_INPUT,
      "Error al asignar capacidades: "
  ),
  INVALID_CAPACITY("INVALID-CAPACITY", ExceptionCode.NOT_FOUND, "Error al asignar una capacidad! "),
  ;

  private final String fullErrorCode;
  private final ExceptionCode exceptionCode;
  private final String message;
}
