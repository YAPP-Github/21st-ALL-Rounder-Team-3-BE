package yapp.allround3.member.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yapp.allround3.member.domain.Member;

@Data
@NoArgsConstructor
public class MemberUpdateRequest {
	private Long memberId;
	private String nickname;
	private String imageUrl;
	private String introduction;
}
