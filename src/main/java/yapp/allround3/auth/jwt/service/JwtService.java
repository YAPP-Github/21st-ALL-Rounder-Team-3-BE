package yapp.allround3.auth.jwt.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import yapp.allround3.auth.jwt.AuthToken;
import yapp.allround3.auth.jwt.AuthTokenProvider;
import yapp.allround3.auth.jwt.dto.AuthRefreshResponse;
import yapp.allround3.auth.jwt.dto.AuthTokenResponse;
import yapp.allround3.common.config.properties.JwtProperties;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.member.domain.Member;
import yapp.allround3.session.domain.Session;
import yapp.allround3.session.repository.SessionRepository;
import yapp.allround3.session.service.SessionService;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final SessionService sessionService;
	private final SessionRepository sessionRepository;
	private final AuthTokenProvider authTokenProvider;
	private final JwtProperties jwtProperties;

	public AuthTokenResponse signIn(Member findMember) {
		String appTokenUuid = UUID.randomUUID().toString();
		String refreshTokenUuid = UUID.randomUUID().toString();

		Session session = Session.builder()
			.member(findMember)
			.appTokenUuid(appTokenUuid)
			.refreshTokenUuid(refreshTokenUuid)
			.build();
		sessionService.save(session);

		AuthToken authToken = authTokenProvider.createUserAuthToken(appTokenUuid, refreshTokenUuid);

		return AuthTokenResponse.of(
			authToken.getAppToken(),
			authToken.getRefreshToken());
	}

	public void validateMailWithAppToken(String email, String appToken) {
		Member findMember = getMemberFromAppToken(appToken);
		if (!email.equals(findMember.getEmail())) {
			throw new CustomException("Invalid email authentication");
		}
	}

	private String createAppTokenWithMember(Member member) {
		Session session = sessionService.findByMember(member);

		String newAppTokenUuid = UUID.randomUUID().toString();
		String refreshTokenUuid = session.getRefreshTokenUuid();

		Session newSession = Session.builder()
			.member(member)
			.appTokenUuid(newAppTokenUuid)
			.refreshTokenUuid(refreshTokenUuid)
			.build();

		sessionService.save(newSession);

		return authTokenProvider.createUserAuthToken(newAppTokenUuid, refreshTokenUuid)
			.getAppToken();
	}

	public Member getMemberFromAppToken(String appToken) {
		String appTokenUuid = convertAppTokenToAppTokenUuid(appToken);
		Session session = sessionService.findByAppTokenUuid(appTokenUuid);
		return session.getMember();
	}

	private String convertAppTokenToAppTokenUuid(String appToken) {
		AuthToken authToken = authTokenProvider.convertAuthToken(appToken);
		Claims appClaims = authToken.getAppTokenClaims();
		return appClaims.get("jti", String.class);
	}

	public AuthRefreshResponse refreshToken(String appToken, String refreshToken) {
		AuthToken authToken = authTokenProvider.convertAuthToken(appToken, refreshToken);
		validateAuthToken(authToken);
		AuthToken newAuthToken = createAuthToken();
		saveAuthToken(newAuthToken, authToken);
		deleteAuthToken(authToken);
		return AuthRefreshResponse.from(newAuthToken.getAppToken());
	}

	private void validateAuthToken(AuthToken authToken) {
		Claims appClaims = authToken.getAppTokenClaimsIncludingExpired();
		Claims refreshClaims = authToken.getRefreshTokenClaims();

		String appTokenUuid = appClaims.get("jti", String.class);
		String refreshTokenUuid = refreshClaims.get("jti", String.class);

		if (!sessionService.existsByAppTokenUuidAndRefreshTokenUuid(appTokenUuid, refreshTokenUuid)) {
			throw new CustomException("Unissued app token or refresh token");
		}
	}

	private AuthToken createAuthToken() {
		String appTokenUuid = UUID.randomUUID().toString();
		String refreshTokenUuid = UUID.randomUUID().toString();
		return authTokenProvider.createUserAuthToken(appTokenUuid, refreshTokenUuid);
	}

	private void saveAuthToken(AuthToken newAuthToken, AuthToken authToken) {
		Claims newAppClaims = newAuthToken.getAppTokenClaims();
		Claims appClaims = authToken.getAppTokenClaimsIncludingExpired();
		Claims refreshClaims = authToken.getRefreshTokenClaims();

		String newAppTokenUuid = newAppClaims.get("jti", String.class);
		String appTokenUuid = appClaims.get("jti", String.class);
		String refreshTokenUuid = refreshClaims.get("jti", String.class);

		Session session = sessionService.findByAppTokenUuid(appTokenUuid);

		Member member = session.getMember();
		Session newSession = Session.builder()
			.member(member)
			.appTokenUuid(newAppTokenUuid)
			.refreshTokenUuid(refreshTokenUuid)
			.build();

		sessionService.save(newSession);
	}

	private void deleteAuthToken(AuthToken authToken) {
	}

	public Cookie getCookieFromRefreshToken(String refreshToken) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

		String refreshTokenExpiry = jwtProperties.getRefreshTokenExpiry();
		refreshTokenCookie.setMaxAge(Integer.parseInt(refreshTokenExpiry) / 1000);

		refreshTokenCookie.setPath("/");

		refreshTokenCookie.setHttpOnly(true);

		return refreshTokenCookie;
	}

	public void deleteSession(Member member) {
		List<Session> sessions = sessionRepository.findByMember(member);
		sessionRepository.deleteAll(sessions);
	}
}
