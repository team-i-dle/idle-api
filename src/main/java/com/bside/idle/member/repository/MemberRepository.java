package com.bside.idle.member.repository;

import java.util.List;
import java.util.Optional;

import com.bside.idle.entity.DefaultCriteria;
import com.bside.idle.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bside.idle.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Query("select m from Member m "
            + "		join fetch m.memberCriteria "
            + "	where m.id=:memberId")
    Optional<Member> findMemberByIdWithCriteria(@Param("memberId") Long memberId);

    @Query("select dc from DefaultCriteria dc")
    List<DefaultCriteria> findDefaultCriteria();

    @Query("select n from Notice n join fetch n.member where n.member.id = :memberId")
    List<Notice> findByMemberId(@Param("memberId") Long memberId);

}