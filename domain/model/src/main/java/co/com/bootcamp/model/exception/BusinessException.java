package co.com.bootcamp.model.exception;

import co.com.bootcamp.model.error.ErrorCode;

public class BusinessException extends ApplicationException {

  public BusinessException(ErrorCode errorCode) {
    super(errorCode);
  }
}
