package co.com.bootcamp.model.exception;

import co.com.bootcamp.model.error.ErrorCode;

public class ObjectNotFoundException extends ApplicationException {
  public ObjectNotFoundException(ErrorCode errorCode, String value) {
    super(errorCode, value);
  }

}
