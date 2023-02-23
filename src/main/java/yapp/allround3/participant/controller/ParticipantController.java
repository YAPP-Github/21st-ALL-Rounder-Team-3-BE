package yapp.allround3.participant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.participant.controller.dto.ParticipantFeedbackResponse;
import yapp.allround3.participant.controller.dto.ParticipantResponse;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.domain.ParticipantStatus;
import yapp.allround3.participant.service.ParticipantService;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ParticipantController {

	private final ParticipantService participantService;

	@PostMapping("/projects/{projectId}")
	public void joinProject(
		@PathVariable Long projectId,
		HttpServletRequest request
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		participantService.joinProject(projectId, memberId);
	}

	@PostMapping("/projects/{projectId}/withdraw")
	public void withdrawProject(
		HttpServletRequest request,
		@PathVariable Long projectId
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		Participant participant = participantService.findParticipantByProjectAndMember(projectId, memberId);

		if (participant.getParticipantStatus() != ParticipantStatus.NORMAL) {
			throw new CustomException("이미 탈퇴한 멤버에요.");
		}

		participantService.withdrawProject(participant.getId());
	}

	@GetMapping("/projects/{projectId}/participants")
	public CustomResponse<List<ParticipantResponse>> findAllParticipants(
		@PathVariable Long projectId
	) {
		List<Participant> participants = participantService.findParticipantsByProject(projectId);
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
