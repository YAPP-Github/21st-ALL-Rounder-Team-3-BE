package yapp.allround3.task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import yapp.allround3.common.entity.BaseTimeEntity;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.project.domain.Project;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "task_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    private LocalDate startDate;

    private LocalDate dueDate;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    private LocalDate feedbackRequestedDate;

    @Formula("(SELECT count(*) FROM feedback f WHERE f.task_id= task_id)")
    private int confirmCount;



    @Builder
    public Task(Participant participant, LocalDate startDate, LocalDate dueDate, String title, String memo, TaskStatus status) {
        this.participant = participant;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.title = title;
        this.memo = memo;
        this.status = status;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void updateDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void updateTaskStatus(TaskStatus taskStatus) {
        this.status = taskStatus;
    }

    public void updateFeedbackRequestedDate(LocalDate feedbackRequestedDate){
        this.feedbackRequestedDate = feedbackRequestedDate;
    }
}
