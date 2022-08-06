package com.bside.idle.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bside.idle.common.ApiResponse;
import com.bside.idle.common.ErrorResponse;
import com.bside.idle.entity.Notice;
import com.bside.idle.member.exception.MemberNotFoundException;
import com.bside.idle.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

	private final MemberService memberService;

	@ExceptionHandler(MemberNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse<ErrorResponse> handleNoSuchElementException(MemberNotFoundException e) {
		return ApiResponse.createError(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
	}

	@GetMapping("/{memberId}/keyword")
	public ResponseEntity<ApiResponse<List<String>>> getKeywordList(@PathVariable("memberId") Long memberId) {
		List<String> criteria = memberService.getKeywordList(memberId);
		ApiResponse<List<String>> body = ApiResponse.createSuccess(criteria);
		return ResponseEntity.ok(body);
	}

	@GetMapping("/{memberId}/notice")
	public ResponseEntity<ApiResponse<String>> listNotice(@PathVariable("memberId") Long memberId) {

		List<Notice> notices = memberService.listNotice(memberId);

		notices.forEach(n -> {
			n.getMember().getMemberCriteria().forEach(mc -> mc.getCriteriaName());
			n.getNoticeCriteria().forEach(nc -> nc.getCriteriaName());
		});

		ApiResponse<String> body = ApiResponse.createSuccess("개발중");
		return ResponseEntity.ok(body);
	}

}
