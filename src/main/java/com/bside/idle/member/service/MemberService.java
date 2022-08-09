package com.bside.idle.member.service;

import static com.bside.idle.member.dto.request.MemberKeywordModifyRequest.*;
import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bside.idle.entity.Member;
import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.entity.Notice;
import com.bside.idle.member.exception.MemberNotFoundException;
import com.bside.idle.member.repository.MemberRepository;
import com.bside.idle.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final NoticeRepository noticeRepository;

	public List<MemberCriteria> getKeywords(Long memberId) {
		Member member = memberRepository
			.findMemberByIdWithCriteria(memberId)
			.orElseThrow(() -> new MemberNotFoundException("멤버 ID(" + memberId + ")는 존재하지 않습니다."));

		return member.getMemberCriteria()
			.stream()
			.filter(MemberCriteria::getUsed)
			.collect(toList());

		// return member.getMemberCriteria()
		// 	.stream()
		// 	.sorted(Comparator.comparing(MemberCriteria::getWeight))
		// 	.map(mc -> mc.getCriteriaName())
		// 	.collect(toList());
	}

	public List<Notice> listNotice(Long memberId) {

		// Member member = memberRepository
		// 	.findById(memberId)
		// 	.orElseThrow(() -> new MemberNotFoundException("멤버 ID(" + memberId + ")는 존재하지 않습니다."));

		return noticeRepository.findByMemberId(memberId);
	}

	public List<MemberCriteria> updateKeywords(Long memberId, Map<Long, MemberKeywordRequest> keywords) {
		Member member = memberRepository
			.findMemberByIdWithCriteria(memberId)
			.orElseThrow(() -> new MemberNotFoundException("멤버 ID(" + memberId + ")는 존재하지 않습니다."));

		member.getMemberCriteria()
			.stream()
			.forEach(memberCriteria -> {
				Long id = memberCriteria.getId();
				MemberKeywordRequest modifiedKeyword = keywords.get(id);
				modifyKeyword(memberCriteria, modifiedKeyword);
				log.info("memberCriteria={}", memberCriteria);
			});

		Member savedMember = memberRepository.save(member);
		return savedMember.getMemberCriteria();
	}

	private void modifyKeyword(MemberCriteria memberCriteria, MemberKeywordRequest modifiedKeyword) {
		memberCriteria.setCriteriaName(modifiedKeyword.getCriteriaName());
		memberCriteria.setWeight(modifiedKeyword.getWeight());
		memberCriteria.setUsed(modifiedKeyword.isUsed());
	}
}
