package yapp.allround3.auth.oauth.client;

import lombok.Builder;
import lombok.Getter;
import yapp.allround3.auth.oauth.Provider;
import yapp.allround3.member.domain.Member;

@Getter
@Builder
public class OauthUserInfo {
	private final String nickname;
	private final String email;
	private final String imageUrl;
	private final Provider provider;
	private final String oauthId;

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
