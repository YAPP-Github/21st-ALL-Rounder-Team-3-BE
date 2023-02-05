/*
package yapp.allround3.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import yapp.allround3.feedback.domain.Feedback;
import yapp.allround3.feedback.repository.FeedbackRepository;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.repository.MemberRepository;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.project.domain.Difficulty;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.repository.ProjectRepository;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.domain.TaskStatus;
import yapp.allround3.task.repository.TaskRepository;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Test
    @Rollback(value = false)
    public void findTaskTest() {

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
        Participant participant2 = Participant.from(project,member2,1);

        participantRepository.save(participant2);
        participantRepository.save(participant1);


        LocalDate startDateTime = LocalDate.of(2022, 10, 10);
        LocalDate endDateTime = LocalDate.of(2022, 10, 15);

        Task task = Task.builder().
                participant(participant1).
                startDate(startDateTime).
                dueDate(endDateTime).
                title("2차 세계 대전").
                memo("어려워").
                status(TaskStatus.BEFORE).
                build();

        taskRepository.save(task);

        Feedback feedback = Feedback.builder().
                task(task).
                participant(participant2).
                contents("hello").
                build();
        feedbackRepository.save(feedback);

       Optional<Task> taskFound = taskRepository.findById(1L);
       assertThat(taskFound.orElseThrow(NoSuchElementException::new).getConfirmCount()).isEqualTo(1);
    }

    @Test
    public void findTaskByParticipant(){

        Optional<Project> project = projectRepository.findById(2L);
        Optional<Member> member = memberRepository.findById(52L);
        Participant participant = participantRepository.findParticipantByProjectAndMember(
                project.orElseThrow(NoSuchElementException::new),
                member.orElseThrow(NoSuchElementException::new));
    }

    @Test
    public void findTaskByIdTest(){
        Optional<Task> task = taskRepository.findTaskById(1L);
    }
}
*/
