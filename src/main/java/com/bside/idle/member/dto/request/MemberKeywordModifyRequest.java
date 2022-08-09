package com.bside.idle.member.dto.request;

import java.util.List;

import com.bside.idle.entity.MemberCriteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberKeywordModifyRequest {

	List<MemberKeywordRequest> memberCriteria;

	@Getter
	@Setter
	public static class MemberKeywordRequest {
		private Long memberCriteriaId;
		private String criteriaName;
		private Long weight;
		private boolean used;
	}
}
