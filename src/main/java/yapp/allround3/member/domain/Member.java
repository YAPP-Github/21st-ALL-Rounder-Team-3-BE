package yapp.allround3.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yapp.allround3.auth.oauth.Provider;
import yapp.allround3.common.entity.BaseTimeEntity;
import yapp.allround3.member.controller.dto.MemberUpdateRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;

	private String name;

	private String email;

	private String imageUrl;

	@Enumerated(EnumType.STRING)
	private Provider provider;

	private String oauthId;

	private String introduction;

	@Enumerated(EnumType.STRING)
	private MemberStatus memberStatus = MemberStatus.NORMAL;

	@Builder
	public Member(String name, String email, String imageUrl, Provider provider, String oauthId) {
		this.name = name;
		this.email = email;
		this.imageUrl = imageUrl;
		this.provider = provider;
		this.oauthId = oauthId;
	}

	public void update(MemberUpdateRequest memberUpdateRequest) {
		this.name = memberUpdateRequest.getNickname();
		this.imageUrl = memberUpdateRequest.getImageUrl();
		this.introduction = memberUpdateRequest.getIntroduction();
	}

	public void withdraw() {
		this.name = null;
		this.email = null;
		this.imageUrl = null;
		this.provider = null;
		oauthId = null;
		introduction = null;
		memberStatus = MemberStatus.WITHDRAWAL;
	}
}
