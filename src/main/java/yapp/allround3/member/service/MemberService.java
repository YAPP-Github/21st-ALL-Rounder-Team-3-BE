package yapp.allround3.member.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.member.controller.dto.MemberResponse;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.repository.MemberRepository;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.project.domain.Project;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ParticipantRepository participantRepository;

    public Member findMemberById(Long id){
        Optional<Member> member= memberRepository.findById(id);
        return member.orElseThrow(()->new CustomException("존재하지 않는 멤버입니다."));
    }

    public List<Member> findMembersParticipatingInProject(Project project){
        return participantRepository.
                findParticipantsByProject(project).
                stream().
                map(Participant::getMember).
                toList();
    }

}
