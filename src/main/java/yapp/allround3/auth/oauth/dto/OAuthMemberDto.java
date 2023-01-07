package yapp.allround3.auth.oauth.dto;

import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Builder;
import lombok.Getter;
import yapp.allround3.auth.oauth.Provider;
import yapp.allround3.member.domain.Member;

@Getter
@Builder
public class OAuthMemberDto {
	private final String nickname;
	private final String email;
	private final String imageUrl;
	private final Provider provider;
	private final String oauthId;

	public static OAuthMemberDto from(OAuth2AuthenticationToken oauth2Token) {
		String authorizedClientRegistrationId = oauth2Token.getAuthorizedClientRegistrationId();
		Provider provider = Provider.valueOf(authorizedClientRegistrationId.toUpperCase());

		OAuth2User oAuth2User = oauth2Token.getPrincipal();
		Map<String, Object> attributes = oAuth2User.getAttributes();

		return switch (provider) {
			case KAKAO -> fromKakao(attributes);
			// case GOOGLE -> ofGoogle(attributes);
		};
	}

	@SuppressWarnings("unchecked")
	private static OAuthMemberDto fromKakao(Map<String, Object> attributes) {
		Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> profile = (Map<String, Object>) account.get("profile");
		String userNameAttributeName = "id";

		return OAuthMemberDto.builder()
			.nickname((String) profile.get("nickname"))
			.email((String) account.get("email"))
			.imageUrl((String) profile.get("profile_image_url"))
			.provider(Provider.KAKAO)
			.oauthId(attributes.get(userNameAttributeName).toString())
			.build();
	}

	public Member toMember() {
		return Member.builder()
			.name(nickname)
			.email(email)
			.imageUrl(imageUrl)
			.provider(provider)
			.oauthId(oauthId)
			.build();
	}
}
