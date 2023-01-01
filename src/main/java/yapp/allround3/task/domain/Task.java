package yapp.allround3.task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import yapp.allround3.common.entity.BaseTimeEntity;
import yapp.allround3.participant.domain.Participant;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="participant_id")
    private Participant participant;

    private LocalDate startDate;

    private LocalDate dueDate;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @Formula("(SELECT count(*) FROM feedback f WHERE f.task_id= id)")
    private int confirmCount;

    @Builder
    public Task(Participant participant, LocalDate startDate, LocalDate dueDate, String title, String memo, int completionCount, TaskStatus status) {
        this.participant = participant;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.title = title;
        this.memo = memo;
        this.confirmCount = completionCount;
        this.status = status;
    }
}
