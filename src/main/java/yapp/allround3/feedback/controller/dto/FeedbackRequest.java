package yapp.allround3.feedback.controller.dto;

import java.util.List;

import lombok.Data;
import yapp.allround3.feedback.domain.FeedbackEvaluation;

@Data
public class FeedbackRequest {
	private FeedbackEvaluation feedbackEvaluation;
	private List<Integer> checklist;
	private String detail;
	private Long memberId;
	private Long taskId;
}
