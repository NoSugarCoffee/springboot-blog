package com.dll.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException{	
	private Logger logger=LoggerFactory.getLogger(GlobalException.class);
	
	@ExceptionHandler(TipException.class)
	public String tipException(Exception e) {
		logger.error("find Exception{}",e.getMessage());
		return "comm/error_404";
	}
		
	@ExceptionHandler(Exception.class)
	public String exception(Exception e) {
		logger.error("find Exception{}",e.getMessage());
		return "comm/error_500";
	}
}
