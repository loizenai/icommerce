package com.ozen.icommerce.exception;

import java.util.List;

public interface ErrorCode {
  String getErrorCode();
  String getErrorMessage();
  List<String> getErrors();
  int getHttpStatusCode();
}
