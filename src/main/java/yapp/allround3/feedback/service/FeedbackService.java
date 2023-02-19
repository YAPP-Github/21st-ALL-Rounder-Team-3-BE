package yapp.allround3.feedback.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.feedback.controller.dto.FeedbackRequest;
import yapp.allround3.feedback.controller.dto.FeedbackResponse;
import yapp.allround3.feedback.domain.Feedback;
import yapp.allround3.feedback.domain.FeedbackTemplate;
import yapp.allround3.feedback.repository.FeedbackRepository;
import yapp.allround3.feedback.repository.FeedbackTemplateRepository;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.participant.service.ParticipantService;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.repository.TaskRepository;
import yapp.allround3.task.service.TaskService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {

	private final FeedbackRepository feedbackRepository;
	private final FeedbackTemplateRepository feedbackTemplateRepository;
	private final MemberService memberService;
	private final TaskService taskService;
	private final ParticipantService participantService;

	@Transactional
	public void saveFeedback(FeedbackRequest feedbackRequest) {

		Member member = memberService.findMemberById(feedbackRequest.getMemberId());
		Task task = taskService.findTaskById(feedbackRequest.getTaskId());
		Participant participant = participantService.findParticipantByProjectAndMember(
			task.getParticipant().getProject(), member);

		if (participant == task.getParticipant()) {
			throw new CustomException("자신의 업무는 피드백 할 수 없습니다.");
		}

		if (existFeedbackHistory(task, participant)) {
			throw new CustomException("이미 피드백을 제출하였습니다.");
		}

		List<Integer> checklist = feedbackRequest.getChecklist();
		checklist.forEach(templateId -> saveFeedbackTemplate(task, templateId));

		Feedback feedback = Feedback.builder()
			.task(task)
			.contents(feedbackRequest.getDetail())
			.feedbackEvaluation(feedbackRequest.getFeedbackEvaluation())
			.participant(participant)
			.build();

		feedbackRepository.save(feedback);
	}

	public CustomResponse<FeedbackResponse> findFeedbacks(Long taskId) {
		Task task = taskService.findTaskById(taskId);

		List<FeedbackTemplate> templates = feedbackTemplateRepository.findByTask(task);
		List<Feedback> feedbacks = feedbackRepository.findByTask(task);

		FeedbackResponse result = FeedbackResponse.of(templates, feedbacks);

		return CustomResponse.success(result);
	}

	private boolean existFeedbackHistory(Task task, Participant participant) {
		return feedbackRepository.findByTaskAndParticipant(task, participant)
			.isPresent();
	}

	private void saveFeedbackTemplate(Task task, Integer templateId) {
		FeedbackTemplate feedbackTemplate = feedbackTemplateRepository
			.findByTaskAndTemplateId(task, templateId)
			.orElseGet(() -> FeedbackTemplate.from(task, templateId));

		feedbackTemplate.addCount();
		feedbackTemplateRepository.save(feedbackTemplate);
	}
}
