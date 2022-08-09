package com.bside.idle.member.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bside.idle.common.ApiResponse;
import com.bside.idle.common.ErrorResponse;
import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.entity.Notice;
import com.bside.idle.member.dto.request.MemberKeywordModifyRequest;
import com.bside.idle.member.dto.request.MemberKeywordModifyRequest.MemberKeywordRequest;
import com.bside.idle.member.dto.response.MemberKeywordResponse;
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
	public ResponseEntity<ApiResponse<List<MemberKeywordResponse>>> getKeywords(
		@PathVariable("memberId") Long memberId) {
		List<MemberCriteria> memberCriteriaList = memberService.getKeywords(memberId);
		List<MemberKeywordResponse> response = getMemberKeywordResponses(memberCriteriaList);
		ApiResponse<List<MemberKeywordResponse>> body = ApiResponse.createSuccess(response);
		return ResponseEntity.ok(body);
	}

	@PutMapping("/{memberId}/keyword")
	public ResponseEntity<ApiResponse<List<MemberKeywordResponse>>> modifyKeywords(
		@PathVariable("memberId") Long memberId, @RequestBody MemberKeywordModifyRequest memberKeywordModifyRequest) {

		Map<Long, MemberKeywordRequest> keywords = memberKeywordModifyRequest
			.getMemberCriteria()
			.stream()
			.collect(Collectors.toMap(MemberKeywordRequest::getMemberCriteriaId, Function.identity()));

		List<MemberCriteria> memberCriteriaList = memberService.updateKeywords(memberId, keywords);
		List<MemberKeywordResponse> response = getMemberKeywordResponses(memberCriteriaList);
		ApiResponse<List<MemberKeywordResponse>> body = ApiResponse.createSuccess(response);
		return ResponseEntity.ok(body);
	}

	@GetMapping("/{memberId}/notice")
	public ResponseEntity<ApiResponse<String>> listNotice(@PathVariable("memberId") Long memberId) {

		List<Notice> notices = memberService.listNotice(memberId);

		notices.forEach(n -> {
			n.getMember().getMemberCriteria().forEach(mc -> mc.getCriteriaName());
			n.getNoticeCriteriaList().forEach(nc -> nc.getCriteriaName());
		});

		ApiResponse<String> body = ApiResponse.createSuccess("개발중");
		return ResponseEntity.ok(body);
	}

	private List<MemberKeywordResponse> getMemberKeywordResponses(List<MemberCriteria> memberCriteriaList) {
		List<MemberKeywordResponse> response = memberCriteriaList.stream()
			.sorted(Comparator.comparingLong(MemberCriteria::getWeight))
			.map(memberCriteria -> MemberKeywordResponse.from(memberCriteria))
			.collect(Collectors.toList());
		return response;
	}

}
