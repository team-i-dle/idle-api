package com.bside.idle.member.controller;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.NoSuchElementException;

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
import com.bside.idle.entity.Member;
import com.bside.idle.exception.MemberNotFoundException;
import com.bside.idle.member.repository.MemberRepository;
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
	public ResponseEntity<ApiResponse<List<String>>> getKeywordList(@PathVariable Long memberId) {
		List<String> criteria = memberService.getKeywordList(memberId);
		ApiResponse<List<String>> body = ApiResponse.createSuccess(criteria);
		return ResponseEntity.ok(body);
	}

}
