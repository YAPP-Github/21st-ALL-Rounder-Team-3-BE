package yapp.allround3.participant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;
import yapp.allround3.participant.controller.dto.ParticipantResponse;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.service.ParticipantService;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.service.ProjectService;

@RestController
@RequiredArgsConstructor
public class ParticipantController {

	private final MemberService memberService;
	private final ProjectService projectService;
	private final ParticipantService participantService;

	@PostMapping("projects/{projectId}")
	public void joinProjects(
		@PathVariable Long projectId,
		HttpServletRequest request
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		Member member = memberService.findMemberById(memberId);
		Project project = projectService.findProjectById(projectId);

		participantService.joinProject(project, member);
	}

	@GetMapping("projects/{projectId}/participants")
	public CustomResponse<List<ParticipantResponse>> findAllParticipants(
		@PathVariable Long projectId
	) {
		Project project = projectService.findProjectById(projectId);
		List<Participant> participants = participantService.findParticipantsByProject(project);

		List<ParticipantResponse> result = participants.stream()
			.map(ParticipantResponse::of)
			.toList();

		return CustomResponse.success(result);
	}
}
