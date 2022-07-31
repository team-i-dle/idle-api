package com.bside.idle.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notice")
public class Notice extends BaseEntity{

	@Id
	@GeneratedValue
	@Column(name = "notice_id")
	private Long id;

	private String title;
	private String url;
	private Long salary;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notice")
	private List<NoticeCriteria> noticeCriteria = new ArrayList<>();
}