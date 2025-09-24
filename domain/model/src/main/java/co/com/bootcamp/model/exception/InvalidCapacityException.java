package co.com.bootcamp.model.exception;

import co.com.bootcamp.model.error.ErrorCode;

public class InvalidCapacityException extends ApplicationException {

  public InvalidCapacityException(ErrorCode errorCode, String value) {
    super(errorCode, value);
  }
}
