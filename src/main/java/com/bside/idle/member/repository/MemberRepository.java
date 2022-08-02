package com.bside.idle.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.idle.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
