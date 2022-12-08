package yapp.allround3.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yapp.allround3.member.domain.Member;
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findMemberById(Long id);
}
