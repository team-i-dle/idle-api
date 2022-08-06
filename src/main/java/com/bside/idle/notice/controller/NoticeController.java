package com.bside.idle.notice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bside.idle.common.ApiResponse;
import com.bside.idle.common.ErrorResponse;
import com.bside.idle.exception.MemberNotFoundException;
import com.bside.idle.notice.dto.NoticeRegisterDto;
import com.bside.idle.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class NoticeController {

	private final NoticeService noticeService;

	@ExceptionHandler(MemberNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse<ErrorResponse> handleNoSuchElementException(MemberNotFoundException e) {
		return ApiResponse.createError(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<NoticeRegisterDto>> registerNotice(
		@RequestBody NoticeRegisterDto noticeRegisterDto) {
		log.info("dto={}", noticeRegisterDto);
		noticeService.register(noticeRegisterDto);
		return ResponseEntity.ok(ApiResponse.createSuccess(noticeRegisterDto));
	}
}
