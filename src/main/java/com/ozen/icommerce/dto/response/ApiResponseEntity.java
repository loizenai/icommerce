package com.ozen.icommerce.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseEntity<T> extends ResponseEntity<ApiResponse<T>> {
  private ApiResponseEntity(ApiResponse<T> body, HttpStatus status) {
    super(body, status);
  }
  
  public static <T> ApiResponseEntity<T> success(HttpStatus status, String message, T body) {
    final ApiResponse<T> res = new ApiResponse<>(status, message, body);
    return new ApiResponseEntity<>(res, status);
  }

  public static ApiResponseEntity<String> success() {
    final var ok = HttpStatus.OK;
    final ApiResponse<String> res = new ApiResponse<>(ok, ok.getReasonPhrase());
    return new ApiResponseEntity<>(res, ok);
  }

  public static <T> ApiResponseEntity<T> success(T body) {
    final ApiResponse<T> res =
        new ApiResponse<>(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), body);
    return new ApiResponseEntity<>(res, HttpStatus.OK);
  }
}
