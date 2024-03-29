package yapp.allround3.common.interceptor;

import java.io.IOException;
import java.util.Objects;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

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
		if (isPreflightRequest(request)) {
			return true;
		}

		if (checkAnnotation(handler)) {
			return true;
		}

		try {
			String appToken = JwtHeaderUtil.getAppToken(request);
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

	private boolean isPreflightRequest(HttpServletRequest request) {
		return isOptions(request) && hasHeaders(request) && hasMethod(request) && hasOrigin(request);
	}

	private boolean isOptions(HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString());
	}

	private boolean hasHeaders(HttpServletRequest request) {
		return Objects.nonNull(request.getHeader("Access-Control-Request-Headers"));
	}

	private boolean hasMethod(HttpServletRequest request) {
		return Objects.nonNull(request.getHeader("Access-Control-Request-Method"));
	}

	private boolean hasOrigin(HttpServletRequest request) {
		return Objects.nonNull(request.getHeader("Origin"));
	}
}

