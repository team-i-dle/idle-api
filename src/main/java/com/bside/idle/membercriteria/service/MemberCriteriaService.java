package com.bside.idle.membercriteria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bside.idle.entity.MemberCriteria;
import com.bside.idle.membercriteria.repository.MemberCriteriaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberCriteriaService {

	private final MemberCriteriaRepository memberCriteriaRepository;

	public List<MemberCriteria> getMemberCriteriaList(List<Long> ids){
		return memberCriteriaRepository.findByIds(ids);
	}
}
