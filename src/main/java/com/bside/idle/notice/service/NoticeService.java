package com.bside.idle.notice.service;

import org.modelmapper.ModelMapper;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;
	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;

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
