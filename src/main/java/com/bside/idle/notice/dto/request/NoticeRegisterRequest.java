package com.bside.idle.notice.dto.request;

import java.util.List;
import java.util.Map;

import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.entity.Notice;
import com.bside.idle.entity.NoticeCriteria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRegisterRequest {

	private Long memberCriteriaId;
	private String title;
	private String url;
	private List<NoticeCriteriaRequest> noticeCriteria;

	public static Notice from(NoticeRegisterRequest noticeRegisterRequest,
		Map<Long, MemberCriteria> memberCriteriaMap) {
		Notice notice = new Notice();
		notice.setTitle(noticeRegisterRequest.getTitle());
		notice.setUrl(noticeRegisterRequest.getUrl());
		noticeRegisterRequest.getNoticeCriteria()
			.forEach(
				noticeCriteriaRequest -> notice.addNoticeCriteria(
					NoticeCriteriaRequest.from(noticeCriteriaRequest, memberCriteriaMap)));
		return notice;
	}

	@Getter
	@Setter
	public static class NoticeCriteriaRequest {

		private Long memberCriteriaId;
		private String criteriaName;
		private int score;
		private String description;

		public static NoticeCriteria from(NoticeCriteriaRequest noticeCriteriaRequest,
			Map<Long, MemberCriteria> memberCriteriaMap) {
			NoticeCriteria noticeCriteria = new NoticeCriteria();
			noticeCriteria.setCriteriaName(noticeCriteriaRequest.getCriteriaName());
			noticeCriteria.setDescription(noticeCriteriaRequest.getDescription());
			noticeCriteria.setScore(noticeCriteriaRequest.getScore());
			noticeCriteria.setMemberCriteria(memberCriteriaMap.get(noticeCriteriaRequest.getMemberCriteriaId()));
			return noticeCriteria;
		}
	}

}
