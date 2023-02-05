package yapp.allround3.auth.oauth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import yapp.allround3.auth.oauth.dto.OAuthMemberDto;
import yapp.allround3.auth.jwt.service.JwtService;
import yapp.allround3.auth.jwt.dto.AuthTokenResponse;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final MemberService memberService;
	private final JwtService jwtService;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		Authentication authentication
	) throws IOException {
		log.info("login success!");

		if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
			OAuthMemberDto oAuthMemberDto = OAuthMemberDto.from(oauth2Token);
			Member oauthMember = oAuthMemberDto.toMember();

			Member member = memberService.join(oauthMember);

			AuthTokenResponse authTokenResponse = jwtService.signIn(member);

			Cookie refreshTokenCookie = jwtService.getCookieFromRefreshToken(authTokenResponse.getRefreshToken());
			httpServletResponse.addCookie(refreshTokenCookie);

			httpServletResponse.sendRedirect("/auth/callback?appToken=" + authTokenResponse.getAppToken());
		}
	}
}
