package yapp.allround3.common.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import yapp.allround3.auth.jwt.JwtHeaderUtil;
import yapp.allround3.auth.jwt.service.JwtService;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.member.domain.Member;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
	private final JwtService jwtService;
	private final ObjectMapper objectMapper;

	@Override
	public boolean preHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler
	) throws IOException {
		if (checkAnnotation(handler)) {
			return true;
		}

		try {
			String appToken = JwtHeaderUtil.getAccessToken(request);
			Member member = jwtService.getMemberFromAppToken(appToken);

			request.setAttribute("memberId", member.getId());
		} catch (CustomException exception) {
			CustomResponse<?> customResponse = CustomResponse.customError(exception);
			String result = objectMapper.writeValueAsString(customResponse);

			response.setStatus(401);
			response.getWriter().write(result);
			return false;
		}

		return true;
	}

	private boolean checkAnnotation(Object handler) {
		try {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			if (handlerMethod.getMethodAnnotation((Class)NoAuth.class) != null) {
				return true;
			}
		} catch (ClassCastException e) {
			return false;
		}
		return false;
	}
}

