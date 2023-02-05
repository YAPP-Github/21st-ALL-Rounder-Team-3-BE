package yapp.allround3.participant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.participant.controller.dto.ParticipantFeedbackResponse;
import yapp.allround3.participant.controller.dto.ParticipantResponse;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.service.ParticipantService;


@RestController
@RequiredArgsConstructor
public class ParticipantController {

	private final ParticipantService participantService;

	@PostMapping("/projects/{projectId}")
	public void joinProjects(
		@PathVariable Long projectId,
		HttpServletRequest request
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		participantService.joinProject(projectId, memberId);
	}

	@GetMapping("/projects/{projectId}/participants")
	public CustomResponse<List<ParticipantResponse>> findAllParticipants(
		@PathVariable Long projectId
	) {
		List<Participant> participants = participantService.findParticipantsByProjectId(projectId);
		List<ParticipantResponse> result = participants.stream()
			.map(ParticipantResponse::of)
			.toList();

		return CustomResponse.success(result);
	}

	@PutMapping("/participants/{participantsId}")
	public void changeLeader(
		@PathVariable Long participantsId,
		HttpServletRequest request
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		participantService.changeLeader(memberId, participantsId);
	}

	@GetMapping("/participants")
	public CustomResponse<ParticipantFeedbackResponse> findParticipantGroupByTask(
		@RequestParam Long taskId
	) {
		ParticipantFeedbackResponse result = participantService.findParticipantGroupByTask(taskId);

		return CustomResponse.success(result);
	}

}
