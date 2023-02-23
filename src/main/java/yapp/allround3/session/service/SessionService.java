package yapp.allround3.session.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.member.domain.Member;
import yapp.allround3.session.domain.Session;
import yapp.allround3.session.repository.SessionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SessionService {

	private final SessionRepository sessionRepository;

	@Transactional
	public void save(Session session) {
		sessionRepository.save(session);
	}

	@Transactional
	public void logout(Long memberId) {
		sessionRepository.deleteAllByMemberId(memberId);
	}

	public Session findByAppTokenUuid(String appTokenUuid) {
		return sessionRepository.findByAppTokenUuid(appTokenUuid)
			.orElseThrow(() -> new CustomException("Unissued app token"));
	}

	public boolean existsByAppTokenUuidAndRefreshTokenUuid(String appTokenUuid, String refreshTokenUuid) {
		return sessionRepository.existsByAppTokenUuidAndRefreshTokenUuid(appTokenUuid, refreshTokenUuid);
	}

	public Session findByMember(Member member) {
		return sessionRepository.findByMember(member)
			.get(0);
	}
}
