package yapp.allround3.feedback.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.feedback.controller.dto.FeedbackRequest;
import yapp.allround3.feedback.controller.dto.FeedbackResponse;
import yapp.allround3.feedback.service.FeedbackService;

@RestController
@RequiredArgsConstructor
public class FeedbackController {

	private final FeedbackService feedbackService;

	@PostMapping("tasks/{taskId}/feedbacks")
	public void addFeedback(
		@PathVariable Long taskId,
		HttpServletRequest request,
		FeedbackRequest feedbackRequest
	) {
		Long memberId = (Long)request.getAttribute("memberId");
		feedbackRequest.setMemberId(memberId);
		feedbackRequest.setTaskId(taskId);

		feedbackService.saveFeedback(feedbackRequest);
	}

	@GetMapping("tasks/{taskId}/feedbacks")
	public CustomResponse<FeedbackResponse> findFeedbacks(
		@PathVariable Long taskId
	) {
		return feedbackService.findFeedbacks(taskId);
	}
}
