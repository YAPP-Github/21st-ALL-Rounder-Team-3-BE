package yapp.allround3.session.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yapp.allround3.common.entity.BaseTimeEntity;
import yapp.allround3.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Session extends BaseTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "session_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(unique = true)
	private String appTokenUuid;

	private String refreshTokenUuid;

	@Builder
	public Session(Member member,
		String appTokenUuid,
		String refreshTokenUuid
	) {
		this.member = member;
		this.appTokenUuid = appTokenUuid;
		this.refreshTokenUuid = refreshTokenUuid;
	}
}
