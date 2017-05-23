package com.lfwer.common;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 防止重复提交过滤器
 * 
 * @author: chuanli
 * @date: 2013-6-27上午11:19:05
 */
public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			AvoidDuplicateSubmission annotation = method.getAnnotation(AvoidDuplicateSubmission.class);
			if (annotation != null) {
				boolean needSaveSession = annotation.needSaveToken();
				if (needSaveSession) {
					TokenProcessor.getInstance().saveToken(request);
				}

				boolean needRemoveSession = annotation.needRemoveToken();
				if (needRemoveSession) {
					if (!TokenProcessor.getInstance().isTokenValid(request)) {
						return false;
					}
					TokenProcessor.getInstance().resetToken(request);
				}
			}
		}
		return true;
	}

}
