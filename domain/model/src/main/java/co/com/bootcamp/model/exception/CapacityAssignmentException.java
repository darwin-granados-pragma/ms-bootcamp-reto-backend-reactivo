package co.com.bootcamp.model.exception;

import co.com.bootcamp.model.error.ErrorCode;

public class CapacityAssignmentException extends ApplicationException {

  public CapacityAssignmentException(ErrorCode errorCode, String value) {
    super(errorCode, value);
  }
}
