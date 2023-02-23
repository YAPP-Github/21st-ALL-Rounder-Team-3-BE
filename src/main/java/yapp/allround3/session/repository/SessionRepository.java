package yapp.allround3.session.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import yapp.allround3.member.domain.Member;
import yapp.allround3.session.domain.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

	Optional<Session> findByAppTokenUuid(String appTokenUuid);

	boolean existsByAppTokenUuidAndRefreshTokenUuid(String appTokenUuid, String refreshTokenUuid);

	List<Session> findByMember(Member member);

	void deleteAllByMemberId(Long memberId);
}
