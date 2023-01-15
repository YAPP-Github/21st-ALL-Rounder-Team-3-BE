package yapp.allround3.participant.controller.dto;

import java.util.List;

import lombok.Data;
import yapp.allround3.member.domain.Member;
import yapp.allround3.participant.domain.Participant;

@Data
public class ParticipantFeedbackResponse {

	private List<ParticipantDto> confirmedParticipants;
	private List<ParticipantDto> unConfirmedParticipants;

	public static ParticipantFeedbackResponse of(
		List<Participant> confirmedParticipants,
		List<Participant> unConfirmedParticipants
	) {
		ParticipantFeedbackResponse participantFeedbackResponse = new ParticipantFeedbackResponse();

		participantFeedbackResponse.confirmedParticipants = confirmedParticipants.stream()
			.map(ParticipantDto::from)
			.toList();

		participantFeedbackResponse.unConfirmedParticipants = unConfirmedParticipants.stream()
			.map(ParticipantDto::from)
			.toList();

		return participantFeedbackResponse;
	}

	private static class ParticipantDto {
		private String name;
		private String imageUrl;

		private static ParticipantDto from(Participant participant) {
			ParticipantDto participantDto = new ParticipantDto();

			Member member = participant.getMember();

			participantDto.name =  member.getName();
			participantDto.imageUrl = member.getImageUrl();

			return participantDto;
		}
	}
}
