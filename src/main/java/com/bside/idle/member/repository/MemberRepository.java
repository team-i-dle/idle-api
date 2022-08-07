package com.bside.idle.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bside.idle.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

	// @Query("select m from Member m "
	// 	+ "join fetch m.memberCriteria mc "
	// 	+ "join fetch m.notices "
	// 	+ "where m.id = :memberId")
	// Optional<Member> findMemberById(@Param("memberId") Long memberId);

