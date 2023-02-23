package yapp.allround3.member.controller;

import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.member.controller.dto.MemberResponse;
import yapp.allround3.member.controller.dto.MemberUpdateRequest;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;
import yapp.allround3.participant.service.ParticipantService;
import yapp.allround3.session.service.SessionService;

@RestController
@RequestMapping("/members")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final ParticipantService participantService;
	private final SessionService sessionService;

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

	@ResponseBody
	@PutMapping("")
	public void updateMember(
		HttpServletRequest request,
		@RequestBody MemberUpdateRequest memberUpdateRequest
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		memberUpdateRequest.setMemberId(memberId);
		memberService.updateMember(memberUpdateRequest);
	}

	@PostMapping("")
	public void logout(
		HttpServletRequest request
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		logoutMember(memberId);
	}

	@DeleteMapping("")
	public void withdraw(
		HttpServletRequest request
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		memberService.withdraw(memberId);
		participantService.withdrawAllProjects(memberId);
		logoutMember(memberId);
	}

	private void logoutMember(Long memberId) {
		Member member = memberService.findMemberById(memberId);
		sessionService.logout(member);
	}
}
