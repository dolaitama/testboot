package com.sugon.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sugon.response.Result;

/**
 * 统一异常处理
 * @author lsx
 * @date 2019年5月27日
 */
@RestControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler
	public Result handleException(Exception e){
		e.printStackTrace();
		return Result.create().fail(e.getMessage());
	}

}
