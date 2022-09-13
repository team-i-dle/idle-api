package com.bside.idle.member.service;

import static java.util.stream.Collectors.*;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bside.idle.entity.Member;
import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.entity.Notice;
import com.bside.idle.member.exception.MemberNotFoundException;
import com.bside.idle.member.repository.MemberRepository;
import com.bside.idle.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final NoticeRepository noticeRepository;

	public List<String> getKeywordList(Long memberId) {
		Member member = memberRepository
			.findMemberByIdWithCriteria(memberId)
			.orElseThrow(() -> new MemberNotFoundException("멤버 ID( " + memberId + " )는 존재하지 않습니다."));

		return member.getMemberCriteria()
			.stream()
			.sorted(Comparator.comparing(MemberCriteria::getWeight))
			.map(mc -> mc.getCriteriaName())
			.collect(toList());
	}

	public List<Notice> listNotice(Long memberId) {

		// Member member = memberRepository
		// 	.findById(memberId)
		// 	.orElseThrow(() -> new MemberNotFoundException("멤버 ID(" + memberId + ")는 존재하지 않습니다."));

		return noticeRepository.findByMemberId(memberId);
	}
}
