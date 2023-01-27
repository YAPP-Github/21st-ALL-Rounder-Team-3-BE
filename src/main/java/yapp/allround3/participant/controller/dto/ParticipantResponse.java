package yapp.allround3.participant.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import yapp.allround3.member.domain.Member;
import yapp.allround3.participant.domain.Participant;

@Data
@NoArgsConstructor
public class ParticipantResponse {
	private Long id;
	private String name;
	private String imageUrl;
	private boolean leader;

	public static ParticipantResponse of(Participant participant) {
		Member member = participant.getMember();
		ParticipantResponse participantResponse = new ParticipantResponse();

		participantResponse.id = participant.getId();
		participantResponse.name = member.getName();
		participantResponse.imageUrl = member.getImageUrl();
		participantResponse.leader = participant.getLeader();

		return participantResponse;
	}
}
