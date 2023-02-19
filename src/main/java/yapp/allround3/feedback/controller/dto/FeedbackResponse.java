package yapp.allround3.feedback.controller.dto;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import lombok.Data;
import yapp.allround3.feedback.domain.Feedback;
import yapp.allround3.feedback.domain.FeedbackEvaluation;
import yapp.allround3.feedback.domain.FeedbackTemplate;

@Data
public class FeedbackResponse {

	Map<FeedbackEvaluation, Integer> evaluations;
	List<FeedbackTemplateDto> templates;
	List<String> details;

	public static FeedbackResponse of(List<FeedbackTemplate> feedbackTemplates, List<Feedback> feedbacks) {
		FeedbackResponse feedbackResponse = new FeedbackResponse();

		feedbackResponse.templates = feedbackTemplates.stream()
			.map(FeedbackTemplateDto::from)
			.sorted(Collections.reverseOrder())
			.toList();

		feedbackResponse.details = feedbacks.stream()
			.map(Feedback::getContents)
			.toList();

		feedbacks.stream()
			.map(Feedback::getFeedbackEvaluation)
			.forEach(evaluation -> {
				Integer count = feedbackResponse.evaluations.getOrDefault(evaluation, 0);
				feedbackResponse.evaluations.put(evaluation, count+1);
			});

		return feedbackResponse;
	}
}
