package yapp.allround3.participant.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yapp.allround3.common.security.SecurityUtils;
import yapp.allround3.participant.domain.Participant;

@Data
@NoArgsConstructor
public class ParticipantDto {
    private Long id;
    private String name;
    private String imageUrl;

    public static ParticipantDto of(Participant participant) {
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setId(participant.getId());
        participantDto.setName(participant.getMember().getName());
        participantDto.setImageUrl(participant.getMember().getImageUrl());
        return participantDto;
    }
}
