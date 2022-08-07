package com.bside.idle.notice.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.bside.idle.entity.Notice;
import com.bside.idle.entity.NoticeCriteria;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeSearchResponse {

	@JsonProperty("notice_id")
	private Long id;
	private String title;
	private String url;
	private List<NoticeCriteriaResponse> noticeCriteria = new ArrayList<>();

	public static NoticeSearchResponse from(Notice notice) {
		NoticeSearchResponse noticeResponse = new NoticeSearchResponse();
		noticeResponse.setId(notice.getId());
		noticeResponse.setTitle(notice.getTitle());
		noticeResponse.setUrl(notice.getUrl());
		notice.getNoticeCriteria()
			.forEach(nc -> noticeResponse.getNoticeCriteria().add(NoticeCriteriaResponse.from(nc)));
		return noticeResponse;
	}

	@Getter
	@Setter
	private static class NoticeCriteriaResponse {

		@JsonProperty("notice_criteria_id")
		private Long id;
		private String criteriaName;
		private int score;
		private String description;

		public static NoticeCriteriaResponse from(NoticeCriteria noticeCriteria) {
			NoticeCriteriaResponse noticeCriteriaResponse = new NoticeCriteriaResponse();
			noticeCriteriaResponse.setId(noticeCriteria.getId());
			noticeCriteriaResponse.setCriteriaName(noticeCriteria.getCriteriaName());
			noticeCriteriaResponse.setScore(noticeCriteria.getScore());
			noticeCriteriaResponse.setDescription(noticeCriteria.getDescription());
			return noticeCriteriaResponse;
		}
	}
}
