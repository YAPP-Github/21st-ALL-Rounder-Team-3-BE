package yapp.allround3.member.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import yapp.allround3.member.domain.Member;

@Data
@AllArgsConstructor
public class MemberUpdateRequest {
	private String nickname;
	private String imageUrl;
	private String introduction;
}
