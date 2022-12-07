package yapp.allround3.task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yapp.allround3.common.entity.BaseTimeEntity;
import yapp.allround3.participant.domain.Participant;

import java.time.LocalDateTime;

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

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String memo;

    private int completionCount;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    @Builder
    public Task(Participant participant, LocalDateTime startDate, LocalDateTime dueDate, String title, String memo, int completionCount, TaskStatus status) {
        this.participant = participant;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.title = title;
        this.memo = memo;
        this.completionCount = completionCount;
        this.status = status;
    }
}
