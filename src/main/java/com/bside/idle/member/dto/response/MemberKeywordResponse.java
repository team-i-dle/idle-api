package com.bside.idle.member.dto.response;

import com.bside.idle.entity.MemberCriteria;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberKeywordResponse {

	@JsonProperty("member_criteria_id")
	private Long id;
	private String criteriaName;

	public static MemberKeywordResponse from(MemberCriteria memberCriteria){
		MemberKeywordResponse response = new MemberKeywordResponse();
		response.setId(memberCriteria.getId());
		response.setCriteriaName(memberCriteria.getCriteriaName());
		return response;
	}
}
