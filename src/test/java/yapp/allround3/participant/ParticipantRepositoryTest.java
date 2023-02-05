/*
package yapp.allround3.participant;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import yapp.allround3.common.security.SecurityUtils;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.repository.MemberRepository;
import yapp.allround3.participant.controller.dto.ParticipantDto;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.project.controller.dto.ProjectResponse;
import yapp.allround3.project.domain.Difficulty;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.repository.ProjectRepository;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.text.html.Option;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class ParticipantRepositoryTest {

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void checkN_1Test(){

        Member member1 = Member.builder().
                name("m1").
                email("sky@g.com").
                imageUrl("https://1").
                build();
        memberRepository.save(member1);

        Member member2 = Member.builder().
                name("m2").
                email("sky@g.com").
                imageUrl("https://1").
                build();
        memberRepository.save(member2);

        LocalDate startDate= LocalDate.of(2022,10,10);
        LocalDate endDate= LocalDate.of(2022,10,31);
        Project project = Project.builder().name("플젝1").startDate(startDate).dueDate(endDate).goal("화팅").difficulty(Difficulty.EASY).build();

        //Optional<Project> project = projectRepository.findById(1L);
        //List<Participant> participants=participantRepository.findParticipantsByProject(project.get());

       // participants.forEach(p-> System.out.println(p.getMember().getName()));

    }

    @Test
    void countProjectParticipantTest(){


        Member member1 = Member.builder().
                name("m1").
                email("sky@g.com").
                imageUrl("https://1").
                build();
        memberRepository.save(member1);

        Member member2 = Member.builder().
                name("m2").
                email("sky@g.com").
                imageUrl("https://1").
                build();
        memberRepository.save(member2);

        LocalDate startDate = LocalDate.of(2022, 10, 10);
        LocalDate endDate = LocalDate.of(2022, 10, 31);

        Project project = Project.builder().name("플젝1").startDate(startDate).dueDate(endDate).goal("화팅").difficulty(Difficulty.EASY).build();
        projectRepository.save(project);

        Participant participant1 = Participant.from(project,member1,1);
        Participant participant2 = Participant.from(project,member2,0);

        participantRepository.save(participant2);
        participantRepository.save(participant1);

        Project foundProject = projectRepository.findById(1L).orElseThrow(NoSuchElementException::new);
        assertThat(participantRepository.countParticipantByProject(project)).isEqualTo(2);
    }

}
*/
