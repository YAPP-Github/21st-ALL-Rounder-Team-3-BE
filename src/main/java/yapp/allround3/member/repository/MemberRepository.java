package yapp.allround3.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yapp.allround3.auth.oauth.Provider;
import yapp.allround3.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByProviderAndOauthId(Provider provider, String oauthId);

}
