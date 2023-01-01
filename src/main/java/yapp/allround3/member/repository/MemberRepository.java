package yapp.allround3.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yapp.allround3.member.domain.Member;
import yapp.allround3.task.domain.Task;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findMemberById(Long id);

    @Query("select f.participant.member from Feedback f where f.task=:task")
    List<Member> findMembersGivenFeedback(Task task);
}
