package com.bside.idle.member.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.bside.idle.entity.Member;
import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.entity.Notice;
import com.bside.idle.entity.NoticeCriteria;
import com.bside.idle.member.exception.MemberNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@PersistenceContext
	EntityManager em;

	@Autowired
	MemberRepository memberRepository;

	Member member;
	List<Notice> notices;

	@BeforeEach
	void setUp() {
		member = new Member(null, "idle@naver.com", "idle", null, new ArrayList<>(), new ArrayList<>());
		notices = List.of(
			new Notice(null, "공고1", "url1", null, null),
			new Notice(null, "공고2", "url2", null, null),
			new Notice(null, "공고3", "url3", null, null));
	}

	@Test
	@DisplayName("회원 검색")
	void testFindMemberById() {
		Member findMember = memberRepository.findById(1L).orElseThrow(MemberNotFoundException::new);

		List<Notice> notices = findMember.getNoticeList();
		notices.forEach(n -> n.getNoticeCriteriaList().forEach(NoticeCriteria::getCriteriaName));

		assertEquals(4, notices.size());
		assertNotice(notices.get(0), "K뱅크 규제시스템 담당자 채용",
			"https://kbank.recruiter.co.kr/app/jobnotice/view?systemKindCode=MRS2&jobnoticeSn=109399", findMember);
		assertNotice(notices.get(1), "[신한은행] App서비스 기획/운영 경력직 채용",
			"https://shinhan.recruiter.co.kr/appsite/company/index", findMember);
		assertNotice(notices.get(2), "(주)아이엠비씨 직원 채용 공고", "https://www.imbc.com/notice/notice_2018/3671051_75022.html",
			findMember);
		assertNotice(notices.get(3), "오토에버 데이터플랫폼/기준정보 부문 경력 채용",
			"https://hyundai-autoever.recruiter.co.kr/app/jobnotice/view?systemKindCode=MRS2&jobnoticeSn=86226",
			findMember);

		List<MemberCriteria> memberCriteria = findMember.getMemberCriteria();
		assertEquals(8, memberCriteria.size());

		assertMemberCriteria(memberCriteria.get(0), "연봉", 1, findMember);
		assertMemberCriteria(memberCriteria.get(1), "산업군", 2, findMember);
		assertMemberCriteria(memberCriteria.get(2), "위치(거리)", 3, findMember);
		assertMemberCriteria(memberCriteria.get(3), "워라밸", 4, findMember);
		assertMemberCriteria(memberCriteria.get(4), "네임밸류", 5, findMember);
		assertMemberCriteria(memberCriteria.get(5), "(회사)성장 가능성", 6, findMember);
		assertMemberCriteria(memberCriteria.get(6), "사내문화", 7, findMember);
		assertMemberCriteria(memberCriteria.get(7), "복지", 8, findMember);
	}

	@Test
	@DisplayName("회원 기본 정보만 저장")
	void testSaveMember() {

		Member savedMember = memberRepository.save(member);

		Long memberId = savedMember.getId();
		Optional<Member> findMember = memberRepository.findById(memberId);

		assertTrue(findMember.isPresent());
		assertMember(findMember.get(), "idle@naver.com", "idle");
	}

	@Test
	@DisplayName("존재하지 않는 회원 검색")
	void testNotFoundMember() {
		Optional<Member> findMember = memberRepository.findById(1L);
		assertThrows(NoSuchElementException.class, findMember::get);
	}

	@Test
	@DisplayName("회원 저장시 채용공고 저장 확인")
	void testSaveMemberWithNotice() {

		member.addNotices(notices);
		Member savedMember = memberRepository.save(member);

		memberRepository.flush();
		em.clear();

		Long memberId = savedMember.getId();
		Member findMember = memberRepository.findById(memberId).get();

		List<Notice> memberNotices = findMember.getNoticeList();
		assertEquals(notices.size(), memberNotices.size());

		log.info("member={}", findMember);
		assertNotice(memberNotices.get(0), "공고1", "url1", member);
		assertNotice(memberNotices.get(1), "공고2", "url2", member);
		assertNotice(memberNotices.get(2), "공고3", "url3", member);
	}

	private void assertNotice(Notice notice, String title, String url, Member member) {
		assertEquals(title, notice.getTitle());
		assertEquals(url, notice.getUrl());
		assertMember(notice.getMember(), member.getEmail(), member.getNickName());
	}

	private void assertMemberCriteria(MemberCriteria memberCriteria, String criteriaName, int weight, Member member) {
		assertEquals(memberCriteria.getCriteriaName(), criteriaName);
		assertEquals(memberCriteria.getWeight(), weight);
		assertMember(memberCriteria.getMember(), member.getEmail(), member.getNickName());
	}

	private void assertMember(Member member, String email, String nickName) {
		assertEquals(email, member.getEmail());
		assertEquals(nickName, member.getNickName());
	}

}