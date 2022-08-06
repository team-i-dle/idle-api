package com.bside.idle.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bside.idle.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

	// List<Notice> findByMemberIdOrderByScore(@Param("memberId") Long memberId);
}
