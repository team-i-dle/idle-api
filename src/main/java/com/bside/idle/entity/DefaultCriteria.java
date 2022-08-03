package com.bside.idle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "default_criteria")
public class DefaultCriteria {

	@Id
	@GeneratedValue
	@Column(name = "default_criteria_id")
	private Long id;

	private String criteriaName;
}
