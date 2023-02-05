package yapp.allround3.feedback;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import yapp.allround3.feedback.repository.FeedbackRepository;
import yapp.allround3.member.repository.MemberRepository;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.repository.TaskRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FeedbackRepositoryTest {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TaskRepository taskRepository;

    /*
    @Test
    void feedbackTest(){
        Optional<Task> task = taskRepository.findById(1L);
        memberRepository.findMembersGivenFeedback(task.orElseThrow(NoSuchElementException::new));
    }*/
}
