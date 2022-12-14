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

	@Column(name="leader", columnDefinition = "TINYINT",length =1)
	private int leader;

	public static Participant from(Project project,Member member,int leader){
		Participant participant=new Participant();
		participant.member=member;
		participant.project=project;
		participant.leader=leader;
		return participant;
	}
}
