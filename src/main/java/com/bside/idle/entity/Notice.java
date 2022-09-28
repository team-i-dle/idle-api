package com.bside.idle.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
@Table(name = "notice")
@SqlResultSetMapping(
		name="ListItemMapping",
		classes = @ConstructorResult(
				targetClass = NoticeListItem.class,
				columns = {
						@ColumnResult(name="notice_id", type = Long.class),
						@ColumnResult(name="title", type = String.class),
						@ColumnResult(name="url", type = String.class),
						@ColumnResult(name="salary", type = Integer.class),
				})
)
public class Notice extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private Long id;

	private String title;
	private String url;
	private Integer salary;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notice", cascade = CascadeType.ALL)
	private List<NoticeCriteria> noticeCriteria = new ArrayList<>();

	public void addNoticeCriteria(NoticeCriteria noticeCriteria) {
		this.noticeCriteria.add(noticeCriteria);
		noticeCriteria.setNotice(this);
	}
}