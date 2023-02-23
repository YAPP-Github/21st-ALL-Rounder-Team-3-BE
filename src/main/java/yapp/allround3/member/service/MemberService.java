package yapp.allround3.member.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.auth.oauth.Provider;
import yapp.allround3.member.controller.dto.MemberUpdateRequest;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.repository.MemberRepository;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.service.ParticipantService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class MemberService {
    private final MemberRepository memberRepository;

    private final ParticipantService participantService;


    public Member findMemberById(Long id){
        Optional<Member> member= memberRepository.findById(id);
        return member.orElseThrow(()->new CustomException("존재하지 않는 멤버입니다."));
    }

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

    @Transactional
    public void updateMember(MemberUpdateRequest memberUpdateRequest) {
        Member member = memberRepository.findById(memberUpdateRequest.getMemberId())
            .orElseThrow(() -> new CustomException("해당 멤버가 존재하지 않습니다."));

        member.update(memberUpdateRequest);
        memberRepository.save(member);
    }

    @Transactional
    public void withdraw(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException("해당 멤버가 존재하지 않습니다."));
        member.withdraw();
        participantService.withdrawAllProjects(member);
    }
}
