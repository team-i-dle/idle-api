package com.bside.idle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = {"notice"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notice_criteria")
public class NoticeCriteria extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_criteria_id")
	private Long id;

	private String criteriaName;
	private int score;
	private String description;

	@ManyToOne
	@JoinColumn(name = "notice_id")
	private Notice notice;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_criteria_id")
	private MemberCriteria memberCriteria;

}