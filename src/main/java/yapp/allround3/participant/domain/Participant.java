package yapp.allround3.participant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yapp.allround3.common.entity.BaseTimeEntity;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.member.domain.Member;
import yapp.allround3.project.domain.Project;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean leader;

    public static Participant initProject(Project project, Member member) {
        Participant participant = from(project, member);
        participant.leader = true;
        return participant;
    }

    public static Participant from(Project project, Member member) {
        Participant participant = new Participant();
        participant.member = member;
        participant.project = project;
        return participant;
    }

    public void changeLeader(Participant former) {
        if (!leader) {
            throw new CustomException("Only the team leader can change leader.");
        }

        former.leader = true;
        this.leader = false;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", project=" + project.getId() +
                ", member=" + member.getId() +
                ", leader=" + leader +
                '}';
    }
}
