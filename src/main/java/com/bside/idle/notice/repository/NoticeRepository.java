package com.bside.idle.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.idle.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
