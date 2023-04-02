package yapp.allround3.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import yapp.allround3.auth.jwt.JwtHeaderUtil;
import yapp.allround3.auth.jwt.dto.AuthRefreshResponse;
import yapp.allround3.auth.jwt.dto.AuthTokenResponse;
import yapp.allround3.auth.jwt.service.JwtService;
import yapp.allround3.auth.oauth.Provider;
import yapp.allround3.auth.oauth.service.SocialUserInfoService;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.interceptor.NoAuth;
import yapp.allround3.member.domain.Member;
import yapp.allround3.session.service.SessionService;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
	private final SocialUserInfoService socialLoginService;

	private final JwtService jwtService;
	private final SessionService sessionService;

	@NoAuth
	@PostMapping("sign-in")
	public CustomResponse<AuthTokenResponse> signIn(
		@RequestBody SignInRequest signinRequest
	) {
		Provider provider = signinRequest.provider;
		String accessToken = signinRequest.accessToken;
		Member member = socialLoginService.signInOrSignUp(provider, accessToken);
		AuthTokenResponse authToken = jwtService.signIn(member);

		return CustomResponse.success(authToken);
	}

	@PostMapping("sign-out")
	public void signOut(
		HttpServletRequest request
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		sessionService.logout(memberId);
	}

	@PostMapping("refresh")
	@ResponseStatus(HttpStatus.CREATED)
	public CustomResponse<AuthRefreshResponse> refreshToken(
			HttpServletRequest request
	) {
		String appToken = JwtHeaderUtil.getAppToken(request);
		String refreshToken = JwtHeaderUtil.getRefreshToken(request);

		AuthRefreshResponse authRefreshResponse = jwtService.refreshToken(appToken, refreshToken);

		return CustomResponse.success(authRefreshResponse);
	}

	private record SignInRequest(
			Provider provider,
			String accessToken
	) { }
}
