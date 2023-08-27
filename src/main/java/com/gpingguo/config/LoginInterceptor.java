package com.gpingguo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result;

		try {
			// 放行
			if ("POST".equals(request.getMethod()) && "/myLogin".equals(request.getRequestURI())) {
				result = true;
			} else {
				result = (boolean)request.getSession().getAttribute("auth");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			result = false;
			//e.printStackTrace();
		}
		log.info("结果{}", result);
		if (!result) {
			response.sendRedirect("/login");
		}
		return result;
	}

}
