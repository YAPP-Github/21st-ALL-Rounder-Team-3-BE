package yapp.allround3.feedback.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yapp.allround3.common.entity.BaseTimeEntity;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.task.domain.Task;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="task_id")
    private Task task;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="participant_id")
    private Participant participant;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Builder
    public Feedback(Task task, Participant participant,String contents) {
            this.task = task;
            this.participant=participant;
            this.contents=contents;
    }
}
