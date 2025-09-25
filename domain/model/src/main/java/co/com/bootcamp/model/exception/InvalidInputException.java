package co.com.bootcamp.model.exception;

import co.com.bootcamp.model.error.ErrorCode;

public class InvalidInputException extends ApplicationException {

  public InvalidInputException(ErrorCode errorCode) {
    super(errorCode);
  }
}
