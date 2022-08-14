package com.ozen.icommerce.exception;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public enum ICommerceErrorCode implements ErrorCode {
  WRONG_INPUT("1000001", "Wrong Input!", Arrays.asList("WRONG_INPUT!"), 400),
  // Sign-In: 200000x
  SIGN_IN_ERROR("2000001", "Sign In Error", Arrays.asList("WRONG_INPUT!"), 403),
  // Sign-Up: 300000x
  SIGN_UP_CONFLICT_USER_EXISTED("3000001", "User Already Existed", Arrays.asList("Conflict_User_Already_Existed"), 409),
  SIGN_UP_CONFLICT_EMAIL_EXISTED("3000002", "Email Already Existed", Arrays.asList("Conflict_Email_Already_Existed"), 409),
	
  // Role-Error: 400000x
  SIGN_UP_NOT_FOUND_USER_ROLE("4000001", "Role Not Found", Arrays.asList("Not_Found_Role"), 404),
	
  // CARD-ITEMs
  CARD_ADD_ERROR("5000010", "Add Card Error", Arrays.asList("Adding_Card_Error"), 500),
  CARD_ADD_ERROR_PRODUCT_ID_EXISTED("5000011", "Conflict - Product ID is Existed", Arrays.asList("Conflict_Product_Id_Existed"), 409),
  CARD_MODIFY_ERROR("5000020", "Modify Card Error", Arrays.asList("Modifing_Card_Error"), 500),
  CARD_MODIFY_ERROR_NOT_FOUND_ITEM_ID("5000021", "Modify Card Error -  Not Found Item-ID In Request", Arrays.asList("Modifing_Card_Error"), 400),
  CARD_DELETE_ERROR("5000030", "Delete Card Error", Arrays.asList("Delete_Card_Error"), 500),
  CARD_ITEM_NOT_FOUND("5000040", "Card Not Found Error", Arrays.asList("Card_Not_Found"), 404);
	  
  @Getter private final String errorCode;
  @Getter private final String errorMessage;
  @Getter private final List<String> errors;
  @Getter private final int httpStatusCode;

  ICommerceErrorCode(String errorCode, String errorMessage, List<String> errors, int statusCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.errors = errors;
    this.httpStatusCode = statusCode;
  }
}
