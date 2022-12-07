package yapp.allround3.member.controller.dto;

import lombok.Data;
import yapp.allround3.member.domain.Member;

@Data
public class MemberResponse {
    private String email;
    private String nickname;
    private String imageUrl;

    public MemberResponse(String email, String nickname, String imageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getEmail(),
                member.getName(),
                member.getImageUrl());
    }
}
