package yapp.allround3.member.controller.dto;

import lombok.Data;
import yapp.allround3.member.domain.Member;

@Data
public class MemberResponse {
    private String email;
    private String nickname;
    private String imageUrl;
    private String introduction;

    public static MemberResponse of(Member member) {
        MemberResponse memberResponse = new MemberResponse();
        memberResponse.email = member.getEmail();
        memberResponse.nickname = member.getName();
        memberResponse.imageUrl = member.getImageUrl();
        memberResponse.introduction = member.getIntroduction();

        return memberResponse;
    }
}
