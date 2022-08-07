package com.bside.idle.notice.dto.request;

import java.util.List;

import com.bside.idle.entity.Notice;
import com.bside.idle.entity.NoticeCriteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRegisterRequest {
	private String title;
	private String url;
	private List<NoticeCriteriaRequest> noticeCriteria;

	public static Notice from(NoticeRegisterRequest noticeRequest) {
		Notice notice = new Notice();
		notice.setTitle(noticeRequest.getTitle());
		notice.setUrl(noticeRequest.getUrl());
		noticeRequest.getNoticeCriteria()
			.forEach(noticeCriteriaRequest -> notice.addNoticeCriteria(
				NoticeCriteriaRequest.from(noticeCriteriaRequest)));
		return notice;
	}

	@Getter
	@Setter
	private static class NoticeCriteriaRequest {
		private String criteriaName;
		private int score;
		private String description;

		public static NoticeCriteria from(NoticeCriteriaRequest noticeCriteriaRequest) {
			NoticeCriteria noticeCriteria = new NoticeCriteria();
			noticeCriteria.setCriteriaName(noticeCriteriaRequest.getCriteriaName());
			noticeCriteria.setDescription(noticeCriteriaRequest.getDescription());
			noticeCriteria.setScore(noticeCriteriaRequest.getScore());
			return noticeCriteria;
		}
	}
}
