package yapp.allround3.feedback.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackTemplate {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="feedback_id")
    private Feedback feedback;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="template_id")
    private Template template;

    @Builder
    public FeedbackTemplate(Feedback feedback,Template template){
        this.feedback=feedback;
        this.template=template;
    }
}
