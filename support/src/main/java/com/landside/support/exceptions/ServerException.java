package com.landside.support.exceptions;

public class ServerException extends IllegalStateException {
  public ServerException(String msg) {
    super(msg);
  }
}
