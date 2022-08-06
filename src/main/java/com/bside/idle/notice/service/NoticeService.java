package com.bside.idle.notice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bside.idle.entity.Member;
import com.bside.idle.entity.Notice;
import com.bside.idle.entity.NoticeCriteria;
import com.bside.idle.exception.MemberNotFoundException;
import com.bside.idle.member.repository.MemberRepository;
import com.bside.idle.notice.dto.NoticeRegisterDto;
import com.bside.idle.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;
	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;

	@Transactional
	public void register(NoticeRegisterDto noticeRegisterDto) {

		Long memberId = noticeRegisterDto.getMemberId();

		Member member = memberRepository
			.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException("멤버 ID(" + memberId + ")는 존재하지 않습니다."));

		List<NoticeCriteria> noticeCriteria = noticeRegisterDto.getCriteria()
			.stream()
			.map(noticeCriteriaDto -> modelMapper.map(noticeCriteriaDto, NoticeCriteria.class))
			.collect(Collectors.toList());

		Notice notice = Notice.createNotice(noticeRegisterDto.getTitle(), noticeRegisterDto.getUrl(), member, noticeCriteria);
		Notice registeredNotice = noticeRepository.save(notice);

	}
}
