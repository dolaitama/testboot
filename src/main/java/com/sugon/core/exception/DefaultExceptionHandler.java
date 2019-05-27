package com.sugon.core.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.sugon.response.Result;
import com.sugon.util.JsonUtil;

/**
 * Created by lsx on 15/5/13.
 */
public class DefaultExceptionHandler extends AbstractHandlerExceptionResolver {
    @Override
    protected ModelAndView doResolveException(
			HttpServletRequest httpServletRequest,
			HttpServletResponse response, Object o, Exception e) {
		Result result = Result.create();
		result.setSuccess(false);
		if (!(e instanceof TiancheException)) {
			e.printStackTrace();
			e = new TiancheException(ErrorStatus.UNKOWN, "调用失败");
		}
		TiancheException ex1 = (TiancheException)e;
		result.setCode(ex1.getErrorStatus().getCode());//设置错误编码
		result.setMessage(ex1.getLocalMessage());//设置错误描述
		result.setError(ex1.getErrorStatus().getMessage());//设置错误信息
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(JsonUtil.bean2Json(result));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return new ModelAndView();
	}
}
