package yapp.allround3.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import yapp.allround3.auth.jwt.JwtHeaderUtil;
import yapp.allround3.auth.jwt.dto.AuthRefreshResponse;
import yapp.allround3.auth.jwt.service.JwtService;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.interceptor.NoAuth;
import yapp.allround3.member.domain.Member;
import yapp.allround3.session.service.SessionService;

@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
@CrossOrigin(origins = "*")
@NoAuth
@Slf4j
public class AuthController {

	private final JwtService jwtService;
	private final SessionService sessionService;

	@PostMapping("refresh")
	@ResponseStatus(HttpStatus.CREATED)
	public CustomResponse<AuthRefreshResponse> refreshToken(HttpServletRequest request) {
		String appToken = JwtHeaderUtil.getAccessToken(request);
		String refreshToken = JwtHeaderUtil.getRefreshToken(request);

		AuthRefreshResponse authRefreshResponse = jwtService.refreshToken(appToken, refreshToken);

		return CustomResponse.success(authRefreshResponse);
	}

	@PostMapping("logout")
	public void logout(
		HttpServletRequest request
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		sessionService.logout(memberId);
	}
}
