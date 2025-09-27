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
  INVALID_SORT_BY("INVALID-SORT-BY", ExceptionCode.INVALID_INPUT, "Parámetro sortBy no válido."),
  INVALID_SORT_DIRECTION_BY("INVALID-SORT-DIRECTION-BY",
      ExceptionCode.INVALID_INPUT,
      "Parámetro sortDirection no válido."
  ),
  INVALID_PARAMETERS("INVALID-PARAMETERS", ExceptionCode.INVALID_INPUT, "Parámetros inválidos"),
  CANNOT_POSIBLE_DELETE_CAPACITY_RELATIONS("CANNOT-POSIBLE-DELETE-CAPACITY-RELATIONS",
      ExceptionCode.UNEXPECTED_ERROR,
      "No fue posible eliminar las capacidades relacionadas al bootcamp"
  ),
  ;

  private final String fullErrorCode;
  private final ExceptionCode exceptionCode;
  private final String message;
}
