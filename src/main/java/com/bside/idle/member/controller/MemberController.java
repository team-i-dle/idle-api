package com.bside.idle.member.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bside.idle.entity.Member;
import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.member.repository.MemberCriteriaRepository;
import com.bside.idle.notice.dto.request.NoticeRegisterRequest;
import com.bside.idle.notice.dto.response.MemberCriteriaResponse;
import com.bside.idle.notice.dto.response.MemberResponse;
import com.bside.idle.notice.dto.response.NoticeListItem;
import com.bside.idle.notice.dto.response.NoticeRegisterResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	private final MemberCriteriaRepository memberCriteriaRepository;

	@ExceptionHandler(MemberNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse<ErrorResponse> handleNoSuchElementException(MemberNotFoundException e) {
		return ApiResponse.createError(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
	}

	@GetMapping("/{memberId}")
	public ResponseEntity<ApiResponse<MemberResponse>> getMemberInfo(@PathVariable("memberId") Long memberId) {

		Member member = memberService.getMemberInfo(memberId);

		MemberResponse memberResponse = new MemberResponse(
				member.getId(),
				member.getEmail(),
				member.getNickName(),
				member.getRole()
		);

		return ResponseEntity.ok(ApiResponse.createSuccess(memberResponse));
	}

	@GetMapping("/{memberId}/keyword")
	public ResponseEntity<ApiResponse<List<MemberCriteriaResponse>>> getKeywordList(@PathVariable("memberId") Long memberId) {

		List<MemberCriteriaResponse> keywordList = memberService.getKeywordList(memberId);
		return ResponseEntity.ok(ApiResponse.createSuccess(keywordList));
	}

	@PostMapping("/{memberId}/keyword")
	public ResponseEntity<ApiResponse<List<MemberCriteriaResponse>>> updateKeywordList(@PathVariable("memberId") Long memberId,
																					   @RequestBody Map<String, Object> param) throws JsonProcessingException {

		JSONObject jsonObject = new JSONObject(param);
		JSONArray jsonArray = jsonObject.getJSONArray("member_criteria");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			Long id = obj.getLong("member_criteria_id");
			Long weight = obj.getLong("weight");

			Optional<MemberCriteria> memberCriteria = Optional.ofNullable(memberCriteriaRepository.findById(id).orElse(null));

			if (memberCriteria.isPresent()) {
				MemberCriteria mc = memberCriteria.get();
				mc.setWeight(weight);
				memberCriteriaRepository.save(mc);
			}
		}

		List<MemberCriteriaResponse> keywordList = memberService.getKeywordList(memberId);
		return ResponseEntity.ok(ApiResponse.createSuccess(keywordList));
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
