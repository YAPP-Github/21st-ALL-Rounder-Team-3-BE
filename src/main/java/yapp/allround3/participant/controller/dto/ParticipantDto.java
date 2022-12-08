package yapp.allround3.participant.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import yapp.allround3.participant.domain.Participant;

@Data
@AllArgsConstructor
public class ParticipantDto {
    private String name;
    private String imageUrl;

    public static ParticipantDto of(Participant participant) {
        return new ParticipantDto(participant.getMember().getName(),
                participant.getMember().getImageUrl());
    }
}
