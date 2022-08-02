package com.bside.idle.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notice")
public class Notice extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "notice_id")
	private Long id;

	private String title;
	private String url;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notice", cascade = CascadeType.ALL)
	private List<NoticeCriteria> noticeCriteria = new ArrayList<>();

	public void addNoticeCriteria(NoticeCriteria noticeCriteria){
		this.noticeCriteria.add(noticeCriteria);
		noticeCriteria.setNotice(this);
	}

	public static Notice createNotice(String title, String url, Member member, List<NoticeCriteria> noticeCriteria) {
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setUrl(url);
		notice.setMember(member);
		noticeCriteria.forEach(notice::addNoticeCriteria);
		return notice;
	}
}