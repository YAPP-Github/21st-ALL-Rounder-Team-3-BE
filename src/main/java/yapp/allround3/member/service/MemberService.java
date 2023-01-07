package yapp.allround3.member.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yapp.allround3.auth.oauth.Provider;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member join(Member member) {
        Provider provider = member.getProvider();
        String oauthId = member.getOauthId();

        Optional<Member> optionalMember = memberRepository.findByProviderAndOauthId(provider, oauthId);

        return optionalMember.orElseGet(() -> saveMember(member));
    }

    @Transactional
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Member findMemberById(Long id){
        return memberRepository.findById(id)
            .orElseThrow();
    }
}
