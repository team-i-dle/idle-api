package com.bside.idle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "member_criteria")
public class MemberCriteria extends BaseEntity{

	@Id
	@GeneratedValue
	@Column(name="member_criteria_id")
	private Long id;

	private String criteriaName;
	private Long weight;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
}
