package yapp.allround3.member.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import yapp.allround3.member.domain.Member;

@Data
@AllArgsConstructor
public class MemberResponse {
    private String email;
    private String nickname;
    private String imageUrl;

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getEmail(),
                member.getName(),
                member.getImageUrl());
    }
}
