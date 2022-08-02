package com.bside.idle.notice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CriteriaDto {
	private String criteriaName;
	private int score;
	private String desc;
}