package yapp.allround3.feedback.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yapp.allround3.common.entity.BaseTimeEntity;
import yapp.allround3.task.domain.Task;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackTemplate extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "feedback_template_id")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="task_id")
    private Task task;

    @Column(unique = true)
    private int templateId;

    private int count;

    public static FeedbackTemplate from(Task task, Integer templateId) {
        FeedbackTemplate feedbackTemplate = new FeedbackTemplate();
        feedbackTemplate.task = task;
        feedbackTemplate.templateId = templateId;

        return feedbackTemplate;
    }

    public void addCount() {
        count++;
    }
}
