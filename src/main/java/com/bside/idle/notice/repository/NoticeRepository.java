package com.bside.idle.notice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bside.idle.entity.Member;
import com.bside.idle.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

	@Query("select distinct n from Notice n join fetch n.noticeCriteriaList where n.id = :noticeId")
	Optional<Notice> findByIdWithCriteria(@Param("noticeId") Long noticeId);

	boolean existsByMemberAndTitle(Member member, String title);

	//Notice 테이블에 memberId가 FK로 잡혀있는데, 굳이 Member 테이블 조인해서 select해야하나..?
	// @Query("select n from Notice n join fetch n.noticeCriteria nc where n.member = :member")
	// List<Notice> findByMemberIdWithCriteria(@Param("member") Member member);

	@Query("select n from Notice n join fetch n.member where n.member.id = :memberId")
	List<Notice> findByMemberId(@Param("memberId") Long memberId);

}
