package yapp.allround3.feedback.controller.dto;

import java.util.List;

import lombok.Data;

@Data
public class FeedbackRequest {
	private List<Integer> checklist;
	private String detail;
	private Long memberId;
	private Long taskId;
}
