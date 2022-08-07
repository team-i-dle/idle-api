package com.bside.idle.entity;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String email;
	private String nickName;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
	private List<Notice> notices = new ArrayList<>();

	@OrderBy("weight ASC")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	private List<MemberCriteria> memberCriteria = new ArrayList<>();

	public Member update(String nickName) {
		this.nickName = nickName;

		return this;
	}

	public String getRoleKey() {
		return this.role.getKey();
	}

	public void addNotice(Notice notice) {
		this.notices.add(notice);
		notice.setMember(this);
	}

	public void addNotices(List<Notice> notices) {
		for (Notice notice : notices) {
			this.addNotice(notice);
		}
	}

	@Builder
	public Member(String email, String nickName, Role role) {
		this.email = email;
		this.nickName = nickName;
		this.role = role;
	}
}
