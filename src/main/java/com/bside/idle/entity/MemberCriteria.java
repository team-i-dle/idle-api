package com.bside.idle.entity;

import javax.persistence.*;

import com.bside.idle.notice.dto.response.MemberCriteriaResponse;
import com.bside.idle.notice.dto.response.NoticeListItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = {"member"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_criteria")
@SqlResultSetMapping(
		name="MemberCriteriaMapping",
		classes = @ConstructorResult(
				targetClass = MemberCriteriaResponse.class,
				columns = {
						@ColumnResult(name="member_criteria_id", type = Long.class),
						@ColumnResult(name="criteria_name", type = String.class),
						@ColumnResult(name="weight", type = Long.class),
				})
)
public class MemberCriteria extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_criteria_id")
	private Long id;

	private String criteriaName;
	private Long weight;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
}
