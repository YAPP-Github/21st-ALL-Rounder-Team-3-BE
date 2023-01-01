package yapp.allround3.member.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yapp.allround3.member.controller.dto.MemberResponse;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.repository.MemberRepository;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.project.domain.Project;
import yapp.allround3.task.domain.Task;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ParticipantRepository participantRepository;

    public MemberResponse findMemberById(Long id){
        Member member= memberRepository.findMemberById(id);
        return MemberResponse.of(member);
    }

    public List<Member> findMembersParticipatingInProject(Project project){
        return participantRepository.
                findParticipantsByProject(project).
                stream().
                map(Participant::getMember).
                toList();
    }

    public List<Member> findMembersGivenFeedback(Task task){
        return memberRepository.findMembersGivenFeedback(task);
    }
}
