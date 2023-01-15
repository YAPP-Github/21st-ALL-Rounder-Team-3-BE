package yapp.allround3.feedback.controller.dto;

import java.util.List;

import lombok.Data;
import yapp.allround3.feedback.domain.Feedback;
import yapp.allround3.feedback.domain.FeedbackTemplate;

@Data
public class FeedbackResponse {

	List<FeedbackTemplateDto> templates;
	List<String> details;

	public static FeedbackResponse of(List<FeedbackTemplate> feedbackTemplates, List<Feedback> feedbacks) {
		FeedbackResponse feedbackResponse = new FeedbackResponse();

		feedbackResponse.templates = feedbackTemplates.stream()
			.map(FeedbackTemplateDto::from)
			.toList();

		feedbackResponse.details = feedbacks.stream()
			.map(Feedback::getContents)
			.toList();

		return feedbackResponse;
	}
}
