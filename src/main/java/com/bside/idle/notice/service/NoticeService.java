package com.bside.idle.notice.service;

import com.bside.idle.notice.dto.response.NoticeListItem;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bside.idle.entity.Member;
import com.bside.idle.entity.Notice;
import com.bside.idle.member.exception.MemberNotFoundException;
import com.bside.idle.member.repository.MemberRepository;
import com.bside.idle.notice.exception.DuplicatedNoticeException;
import com.bside.idle.notice.exception.NoticeNotFoundException;
import com.bside.idle.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;
	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;

	@PersistenceContext
	EntityManager em;

	@Transactional
	public Notice register(Long memberId, Notice notice) {

		Member member = memberRepository
			.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("멤버 ID(" + memberId + ")는 존재하지 않습니다."));

		boolean isDuplicated = noticeRepository.existsByMemberAndTitle(member, notice.getTitle());
		if (isDuplicated) {
			throw new DuplicatedNoticeException("같은 이름의 채용공고가 존재합니다.");
		}

		notice.setMember(member);
		return noticeRepository.save(notice);
	}

	@Transactional(readOnly = true)
	public List<NoticeListItem> searchNoticeList(Long memberId) {
		String q = "select n.notice_id, n.title, n.url, n.salary "
				+ "from notice n "
				+ "inner join members m on m.member_id = n.member_id "
				+ "inner join notice_criteria nc on nc.notice_id = n.notice_id "
				+ "inner join member_criteria mc on mc.member_id = m.member_id "
				+ "where mc.criteria_name = nc.criteria_name "
				+ "and n.member_id = ? "
				+ "group by n.title, n.url "
				+ "order by sum((1 + (mc.weight * 0.1)) * nc.score) desc";

		Query query = em.createNativeQuery(q, "ListItemMapping");
		query.setParameter(1, memberId);

		return (List<NoticeListItem>) query.getResultList();
	}

	@Transactional(readOnly = true)
	public Notice searchNotice(Long noticeId) {
		return noticeRepository.findByIdWithCriteria(noticeId)
			.orElseThrow(() -> new NoticeNotFoundException("채용공고 ID(" + noticeId + ")는 존재하지 않습니다."));
	}

	public void deleteNotice(Long noticeId) {

		Notice notice = noticeRepository.findById(noticeId)
			.orElseThrow(() -> new NoticeNotFoundException("채용공고 ID(" + noticeId + ")는 존재하지 않습니다."));

		noticeRepository.deleteById(notice.getId());
	}
}
