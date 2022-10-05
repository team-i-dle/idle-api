package com.bside.idle.member.service;

import static java.util.stream.Collectors.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.bside.idle.notice.dto.response.MemberCriteriaResponse;
import com.bside.idle.notice.dto.response.NoticeListItem;
import org.springframework.stereotype.Service;

import com.bside.idle.entity.Member;
import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.entity.Notice;
import com.bside.idle.member.exception.MemberNotFoundException;
import com.bside.idle.member.repository.MemberRepository;
import com.bside.idle.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final NoticeRepository noticeRepository;

	@PersistenceContext
	EntityManager em;

	@Transactional(readOnly = true)
	public Member getMemberInfo(Long memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException("멤버 ID(" + memberId + ")는 존재하지 않습니다."));
	}

	@Transactional(readOnly = true)
	public List<MemberCriteriaResponse> getKeywordList(Long memberId) {
		String q = "select mc.member_criteria_id, mc.criteria_name, mc.weight "
				+ "from member_criteria mc "
				+ "where mc.member_id = ? "
				+ "order by mc.weight desc";

		Query query = em.createNativeQuery(q, "MemberCriteriaMapping");
		query.setParameter(1, memberId);

		return (List<MemberCriteriaResponse>) query.getResultList();
	}

	public List<Notice> listNotice(Long memberId) {

		// Member member = memberRepository
		// 	.findById(memberId)
		// 	.orElseThrow(() -> new MemberNotFoundException("멤버 ID(" + memberId + ")는 존재하지 않습니다."));

		return noticeRepository.findByMemberId(memberId);
	}
}
