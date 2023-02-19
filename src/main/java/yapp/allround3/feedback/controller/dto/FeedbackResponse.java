package yapp.allround3.feedback.controller.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import yapp.allround3.feedback.domain.Feedback;
import yapp.allround3.feedback.domain.FeedbackEvaluation;
import yapp.allround3.feedback.domain.FeedbackTemplate;

@Data
public class FeedbackResponse {

	Map<String, Integer> evaluations = Arrays.stream(FeedbackEvaluation.values())
		.collect(Collectors.toMap(FeedbackEvaluation::name, initialCount -> 0));
	List<FeedbackTemplateDto> templates;
	List<String> details;

	public static FeedbackResponse of(List<FeedbackTemplate> feedbackTemplates, List<Feedback> feedbacks) {
		FeedbackResponse feedbackResponse = new FeedbackResponse();;

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
				String evaluationName = evaluation.name();
				Integer count = feedbackResponse.evaluations.get(evaluationName);
				feedbackResponse.evaluations.put(evaluationName, count+1);
			});

		return feedbackResponse;
	}
}
