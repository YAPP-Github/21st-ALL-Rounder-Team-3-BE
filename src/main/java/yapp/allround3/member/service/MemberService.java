package yapp.allround3.member.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yapp.allround3.member.controller.dto.MemberResponse;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse findMemberById(Long id){
        Member member= memberRepository.findMemberById(id);
        return MemberResponse.of(member);
    }
}
