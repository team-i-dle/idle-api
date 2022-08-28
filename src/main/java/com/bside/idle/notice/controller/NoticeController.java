package com.bside.idle.notice.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bside.idle.common.ApiResponse;
import com.bside.idle.common.ErrorResponse;
import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.entity.Notice;
import com.bside.idle.member.exception.MemberNotFoundException;
import com.bside.idle.member.service.MemberService;
import com.bside.idle.membercriteria.service.MemberCriteriaService;
import com.bside.idle.notice.dto.request.NoticeRegisterRequest;
import com.bside.idle.notice.dto.response.NoticeRegisterResponse;
import com.bside.idle.notice.dto.response.NoticeSearchResponse;
import com.bside.idle.notice.exception.DuplicatedNoticeException;
import com.bside.idle.notice.exception.NoticeNotFoundException;
import com.bside.idle.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class NoticeController {

	private final MemberService memberService;
	private final NoticeService noticeService;
	private final MemberCriteriaService memberCriteriaService;

	@ExceptionHandler(MemberNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse<ErrorResponse> handleNoSuchElementException(MemberNotFoundException e) {
		return ApiResponse.createError(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
	}

	@ExceptionHandler(NoticeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse<ErrorResponse> handleNoticeNotFoundException(NoticeNotFoundException e) {
		return ApiResponse.createError(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
	}

	@ExceptionHandler(DuplicatedNoticeException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiResponse<ErrorResponse> handleDuplicatedNoticeException(DuplicatedNoticeException e) {
		return ApiResponse.createError(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<NoticeRegisterResponse>> registerNotice(
		@RequestParam("memberId") Long memberId,
		@RequestBody NoticeRegisterRequest noticeRequest) {

		List<Long> memberCriteriaIds = noticeRequest.getNoticeCriteria()
			.stream()
			.map(NoticeRegisterRequest.NoticeCriteriaRequest::getMemberCriteriaId)
			.collect(Collectors.toList());

		Map<Long, MemberCriteria> memberCriteriaMap = memberCriteriaService.getMemberCriteriaList(memberCriteriaIds)
			.stream()
			.collect(Collectors.toMap(MemberCriteria::getId, Function.identity()));

		Notice notice = NoticeRegisterRequest.from(noticeRequest, memberCriteriaMap);
		log.info("notice={}", notice);
		Notice registeredNotice = noticeService.register(memberId, notice);

		NoticeRegisterResponse noticeResponse = NoticeRegisterResponse.from(registeredNotice);
		return ResponseEntity.ok(ApiResponse.createSuccess(noticeResponse));
	}

	@GetMapping("/{noticeId}")
	public ResponseEntity<ApiResponse<NoticeSearchResponse>> searchNotice(@PathVariable("noticeId") Long noticeId) {
		Notice notice = noticeService.searchNotice(noticeId);
		NoticeSearchResponse noticeSearchResponse = NoticeSearchResponse.from(notice);
		return ResponseEntity.ok(ApiResponse.createSuccess(noticeSearchResponse));
	}

	@DeleteMapping("/{noticeId}")
	public ResponseEntity<ApiResponse<String>> deleteNotice(@PathVariable("noticeId") Long noticeId) {
		noticeService.deleteNotice(noticeId);
		return ResponseEntity.ok(ApiResponse.createSuccess("(" + noticeId + ") 삭제 완료 "));
	}

	@GetMapping("/title")
	public String getTitle(@RequestParam("url") @URL(protocol = "https") String url) {
		log.info("url={}", url);
		return null;
	}

}
