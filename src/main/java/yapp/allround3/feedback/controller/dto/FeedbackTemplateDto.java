package yapp.allround3.feedback.controller.dto;

import lombok.Data;
import yapp.allround3.feedback.domain.FeedbackTemplate;

@Data
public class FeedbackTemplateDto implements Comparable<FeedbackTemplateDto> {
	private int templateId;
	private int count;

	public static FeedbackTemplateDto from(FeedbackTemplate feedbackTemplate) {
		FeedbackTemplateDto feedbackTemplateDto = new FeedbackTemplateDto();

		feedbackTemplateDto.templateId = feedbackTemplate.getTemplateKey();
		feedbackTemplateDto.count = feedbackTemplate.getCount();

		return feedbackTemplateDto;
	}

	@Override
	public int compareTo(FeedbackTemplateDto other) {
		return this.count - other.count;
	}
}
