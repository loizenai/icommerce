package com.ozen.icommerce.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ozen.icommerce.dto.card.CardItem;
import com.ozen.icommerce.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class IcommerceInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    try {
    	CardItem cardItem = (CardItem) handler;
    	final var token = Utils.getJwt(request);
    	cardItem.setSessionId(token);
    } catch(Exception e) {
    	log.error("Error: {}", e);
    }
    return true;
  }
}
