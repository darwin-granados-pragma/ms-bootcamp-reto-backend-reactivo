package co.com.bootcamp.model.exception;

import co.com.bootcamp.model.error.ErrorCode;

public class InvalidFormatParamException extends ApplicationException {

  public InvalidFormatParamException(ErrorCode errorCode) {
    super(errorCode);
  }
}
