package yapp.allround3.auth.oauth.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yapp.allround3.auth.oauth.Provider;
import yapp.allround3.auth.oauth.client.OauthUserInfo;
import yapp.allround3.auth.oauth.client.OauthUserInfoClient;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class SocialUserInfoService {
    private final Map<String, OauthUserInfoClient> socialUserInfoClients;
    private final MemberService memberService;

    public Member signInOrSignUp(Provider provider, String accessToken) {
        OauthUserInfoClient socialUserInfoClient = socialUserInfoClients.get(provider.name());
        OauthUserInfo userInfo = socialUserInfoClient.getUserInfo(accessToken);
        Member member = userInfo.toMember();

        return memberService.join(member);
    }
}
