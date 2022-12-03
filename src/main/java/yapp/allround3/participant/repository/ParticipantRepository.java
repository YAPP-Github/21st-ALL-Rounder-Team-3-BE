package yapp.allround3.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import yapp.allround3.member.domain.Member;
import yapp.allround3.participant.domain.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

	List<Participant> findByMember(Member member);
}
