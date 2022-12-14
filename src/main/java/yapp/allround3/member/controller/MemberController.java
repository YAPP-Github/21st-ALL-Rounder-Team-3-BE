package yapp.allround3.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.member.controller.dto.MemberResponse;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@ResponseBody
	@GetMapping("")
	public CustomResponse<MemberResponse> getMember(
		HttpServletRequest request
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		Member member = memberService.findMemberById(memberId);

		MemberResponse memberResponse = MemberResponse.of(member);

		return CustomResponse.success(memberResponse);
	}
}
