package yapp.allround3.auth.oauth.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		AuthenticationException e
	) throws IOException {
		log.warn("[login failure] ", e);

		httpServletResponse.sendRedirect("/auth/callback?appToken=");
	}
}
